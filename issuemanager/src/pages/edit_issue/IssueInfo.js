import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styles from './EditIssue.module.css';

function IssueInfo( { projectId, user, issue, setIssue, categories, isEditMode } ) {  
    
    const navigate = useNavigate();
    const [devList, setDevList] = useState([]);
    const assigneeString = devList.join(', '); // devList를 문자열로 변환

    function handleChange(e) {
        const { name, value } = e.target;
        setIssue((prevIssue) => ({ ...prevIssue, [name]: value }));
    }

    /* 현재 이슈의 카테고리에 대해서 추천하는 개발자를 fetch */
    useEffect(() => {
        async function fetchDevelopers() {
            try {
                const response = await fetch(`/issues/projects/${projectId}/recommend?category=${issue.category}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });
                
                const data = await response.json();
        
                if (data.isSuccess) {
                    setDevList(data.result.devList);
                } else {
                    throw new Error(data.message || '데이터를 가져오는데 실패했습니다.');
                }
            } catch (error) {
                console.error('개발자 목록을 불러오는 중 에러가 발생했습니다:', error);
            }
        };
    
        fetchDevelopers();
    }, [projectId, issue.category]); // projectId나 issue.category가 바뀌면 이 effect를 다시 실행

    /* 날짜 출력 형식 지정 함수 */
    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');

        return `${year}-${month}-${day}`;
    }

    /* 이슈 수정 API */
    async function handleUpdate(assigneeId, newStatus) {
        const url = `/issues/${issue.id}`;

        try {
            const response = await fetch(url, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id: user.id,
                    status: newStatus,
                    assigneeId: assigneeId, // 담당자 ID, 지정하지 않으면 null
                }),
            })
    
            const data = await response.json(); // response.json() 호출 결과를 기다린 후 변수에 할당
    
            if (data.isSuccess) {
                issue.assingee = assigneeId;
                console.log('Issue updated: ', data.result.issueId, ' Status: ', data.result.status);
                alert('이슈가 성공적으로 업데이트되었습니다. ' + data.message);
            } else {
                alert('이슈 업데이트에 실패했습니다: ' + data.message);
            }
        } catch (error) {
            console.error('이슈 업데이트 실패: ', error);
            alert('이슈 업데이트 중 오류가 발생했습니다.');
        }
    }

    /* 이슈 상태 업데이트를 처리하는 함수 */
    async function updateIssueStatus(assigneeId, newStatus) {
        await handleUpdate(assigneeId, newStatus);
    }

    /* 각각 이슈 상태 수정 버튼 클릭 시 실행될 함수 */
    async function onReopenClick() { await updateIssueStatus(null, 'REOPENED'); }
    async function onAssignClick() { await updateIssueStatus(issue.assignee, 'ASSIGNED'); }
    async function onCloseIssueClick() { await updateIssueStatus(null, 'CLOSED'); }
    async function onResolveIssueClick() { await updateIssueStatus(null, 'RESOLVED'); }
    async function onCodeModifyClick() { await updateIssueStatus(null, 'FIXED'); }
    
    const onSaveClick = async (event) => {
        event.preventDefault(); // 폼 제출 시 새로고침 방지
        navigate('/'); // 이슈 세부 사항 확인 및 편집을 마치고 홈으로 이동
      };
    

    return (
        <div>
            <div className={styles.infoContainer}>
                <label className={styles.label}>Title</label>
                <input className={styles.input}
                    type="text"
                    name="title"
                    value={issue.title}
                    disabled
                />
                <label className={styles.label}>Status</label>
                <input className={styles.input}
                    type="text"
                    name="status"
                    value={issue.status}
                    onChange={handleChange}
                    disabled
                />
                <label className={styles.label}>Description</label>
                <textarea className={styles.textarea}
                    name="description"
                    value={issue.description}
                    disabled
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
                                disabled
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
                    value={formatDate(issue.reportedDate)}
                    disabled // 날짜는 수정 불가능
                />
                <label className={styles.label}>Priority</label>
                <select className={styles.select}
                    name="priority"
                    value={issue.priority}
                    disabled
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
                    disabled={isEditMode === false || user.role !== 'PL'}
                    onChange={handleChange}
                />
                <div className={styles.recommendContainer}>
                    <label className={styles.recommendlabel} >best candidate: {assigneeString}</label>
                </div>
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
                    onClick={onReopenClick}
                >Reopen</button>
                <button 
                    className={styles.btn}
                    onClick={onAssignClick}
                    disabled={user.role !== 'PL'}
                >Assign</button>
                <button 
                    className={styles.btn} 
                    onClick={onCodeModifyClick}
                    disabled={user.role !== 'DEV'}
                >Fix</button>
                <button 
                    className={styles.btn} 
                    onClick={onResolveIssueClick}
                    disabled={user.role !== 'TESTER'}
                >Resolve</button>
                <button 
                    className={styles.btn} 
                    onClick={onCloseIssueClick}
                    disabled={user.role !== 'PL'}
                >Close</button>
                <button 
                    className={styles.btn} 
                    type="submit" 
                    onClick={onSaveClick}
                >SAVE</button>
            </div>
            <div className={styles.btns}>
            </div>
        </div>
    );
}

export default IssueInfo;