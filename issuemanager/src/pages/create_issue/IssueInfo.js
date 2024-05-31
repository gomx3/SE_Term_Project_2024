import React from 'react';
import styles from './CreateIssue.module.css';

function IssueInfo( { projectId, user, issue, setIssue, categories } ) {   
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

            const result = await response.json(); // response.json() 호출 결과를 기다린 후 변수에 할당
            console.log(issue);

            if (result.isSuccess) {
                console.log('Issue created: ', result.issueId);
                alert('이슈가 성공적으로 생성되었습니다. ' + result.message);
            } else {
                alert('이슈 생성에 실패했습니다: ' + result.message); // 수정: 실패 시 서버에서 받은 메시지를 출력
            }
        } catch (error) {
            console.error('이슈 생성 실패: ', error);
            alert('이슈 등록 중 오류가 발생했습니다.');
        }
    }
    
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
                <label className={styles.label}>State</label>
                <input className={styles.input}
                    type="text"
                    name="state"
                    value={issue.state}
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
                <button className={styles.btn} type="submit" onClick={handleSubmit}>Create Issue</button>
            </div>
        </div>
    );
}

export default IssueInfo;