import React from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './CreateIssue.module.css';

function IssueInfo( { projectId, user, issue, setIssue, categories } ) {   

    const navigate = useNavigate();
    
    function handleChange(e) {
        const { name, value } = e.target;
        setIssue((prevIssue) => ({ ...prevIssue, [name]: value }));
    }
    
    function handleCategoryChange(index) {
        setIssue((prevIssue) => {
            const category = categories[index];
            return {
                ...prevIssue,
                category: category,
            };
        });
    }

    async function handleSubmit() {
        const url = `/issues/projects/${projectId}`;

        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    reporterId: user.id,
                    title: issue.title,
                    description: issue.description,
                    priority: issue.priority,
                    category: issue.category,
                }),
            })

            const data = await response.json(); // response.json() 호출 결과를 기다린 후 변수에 할당
            console.log(issue);

            if (data.isSuccess) {
                console.log('Issue created: ', data.issueId);
                alert('이슈가 성공적으로 생성되었습니다. ' + data.message);

                issue.id = data.result.issueId;
            } else {
                alert('이슈 생성에 실패했습니다: ' + data.message); // 수정: 실패 시 서버에서 받은 메시지를 출력
            }
        } catch (error) {
            console.error('이슈 생성 실패: ', error);
            alert('이슈 등록 중 오류가 발생했습니다.');
        }
    }

    const onHomeClick = async (event) => {
        event.preventDefault(); // 폼 제출 시 새로고침 방지
        navigate('/', {
            state: {
                projectId, 
                userId: user.id, 
                userRole: user.role, 
                memberId: user.memberId,
            }
        }); // 이슈 생성 후 홈으로 이동 
    };
    
    return (
        <div>
            <div className={styles.infoContainer}>
                <label className={styles.label}>Title</label>
                <input className={styles.input}
                    type="text"
                    name="title"
                    value={issue.title}
                    onChange={handleChange}
                />
                <label className={styles.label}>Status</label>
                <input className={styles.input}
                    type="text"
                    name="state"
                    value={issue.status}
                    onChange={handleChange}
                    disabled
                />
                <label className={styles.label}>Description</label>
                <textarea className={styles.textarea}
                    name="description"
                    value={issue.description}
                    onChange={handleChange}
                />
                <label className={styles.label}>Issue Category</label>
                <div>
                    {categories.map((category, index) => (
                        <div key={index} className={styles.categoryContainer}>
                            <input
                                className={styles.input}
                                type="radio"
                                id={category}
                                name="category"
                                value={category}
                                checked={issue.category === category}
                                onChange={() => handleCategoryChange(index)} // 인덱스를 인자로 전달
                            />
                            <label className={styles.label} htmlFor={category}>
                                {category}
                            </label>
                        </div>
                    ))}
                </div>
            </div>
            <div className={styles.infoContainer}>
                <label className={styles.label}>Reporter</label>
                <input className={styles.input}
                    type="text"
                    name="reporter"
                    value={issue.reporter}
                    disabled
                />
                <label className={styles.label}>Reported Date</label>
                <input className={styles.input}
                    type="text"
                    name="reportedDate"
                    value={issue.reportedDate}
                    disabled // 날짜는 수정 불가능
                />
                <label className={styles.label}>Priority</label>
                <select className={styles.select}
                    name="priority"
                    value={issue.priority}
                    onChange={handleChange}
                >
                    <option value="MAJOR">Major</option>
                    <option value="BLOCKER">Blocker</option>
                    <option value="CRITICAL">Critical</option>
                    <option value="MINOR">Minor</option>
                    <option value="TRIVIAL">Trivial</option>
                </select>
                <label className={styles.label}>Assignee</label>
                <input className={styles.input}
                    type="text"
                    name="assignee"
                    value={issue.assignee}
                    disabled
                />
                <label className={styles.label}>Fixer</label>
                <input className={styles.input}
                    type="text"
                    name="fixer"
                    value={issue.fixer}
                    disabled
                />
            </div>
            <div className={styles.btns}>
                <button 
                    className={styles.btn} 
                    onClick={handleSubmit}>
                Create Issue</button>
                <button 
                    className={styles.btn} 
                    type="submit" 
                    onClick={onHomeClick}
                >HOME</button>
            </div>
        </div>
    );
}

export default IssueInfo;