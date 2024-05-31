import React from 'react';
import styles from './EditIssue.module.css';

function IssueInfo( { user, issue, setIssue, categories, isEditMode } ) {   

    /* 임시 */
    const devList = ["seoyeon_dev", "seoyeon22", "user123"];
    const RecommendedReporterString = devList.join(', ');

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

    async function handleUpdate(newStatus, assigneeId = null) {
        const url = `/issues/${issue.id}`; // URL 수정
    
        try {
            const response = await fetch(url, {
                method: 'PATCH', // 메서드를 PATCH로 변경
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id: issue.id, // 이슈 ID 추가
                    status: newStatus, // 새로운 상태
                    assigneeId: assigneeId, // 담당자 ID, 지정하지 않으면 null
                }),
            })
    
            const result = await response.json(); // response.json() 호출 결과를 기다린 후 변수에 할당
            console.log(result);
    
            if (result.isSuccess) {
                console.log('Issue updated: ', result.result.issueId, ' Status: ', result.result.status);
                alert('이슈가 성공적으로 업데이트되었습니다. ' + result.message);
            } else {
                alert('이슈 업데이트에 실패했습니다: ' + result.message); // 수정: 실패 시 서버에서 받은 메시지를 출력
            }
        } catch (error) {
            console.error('이슈 업데이트 실패: ', error);
            alert('이슈 업데이트 중 오류가 발생했습니다.');
        }
    }

    // 이슈 상태 업데이트를 처리하는 함수
    async function updateIssueStatus(newStatus) {
        // 여기서 issueId, assigneeId 등은 현재 선택된 이슈의 정보를 바탕으로 설정해야 합니다.
        // 예시에서는 issue.id, issue.assignee 등으로 가정합니다.
        // 실제 구현에서는 이 컴포넌트의 state 또는 props에서 적절한 값을 가져와야 합니다.
        const issueId = issue.id;
        const assigneeId = issue.assignee; // 또는 다른 로직에 따라 결정될 수 있음
        await handleUpdate(newStatus, assigneeId);
    }

    // Save 버튼 클릭 시 실행될 함수
    async function onSaveClick() {
        // 여기서는 예시로 'FIXED' 상태로 업데이트하는 것을 가정합니다.
        // 실제로는 사용자가 선택한 값을 기반으로 상태를 결정해야 합니다.
        await updateIssueStatus('FIXED');
    }

    // Close Issue 버튼 클릭 시 실행될 함수
    async function onCloseIssueClick() {
        await updateIssueStatus('CLOSED');
    }

    // Resolve Issue 버튼 클릭 시 실행될 함수
    async function onResolveIssueClick() {
        await updateIssueStatus('RESOLVED');
    }

    async function handleSave(newStatus, assigneeId = null) {
        const url = `/issues//${issue.id}`;

        try {
            const response = await fetch(url, {
                method: 'PATCH', // 메서드를 PATCH로 변경
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id: issue.id,
                    status: "FIXED",
                    assigneeId: "dev",
                }),
            })
    
            const result = await response.json(); // response.json() 호출 결과를 기다린 후 변수에 할당
            console.log(result);
    
            if (result.isSuccess) {
                console.log('Issue updated: ', result.result.issueId, ' Status: ', result.result.status);
                alert('이슈가 성공적으로 업데이트되었습니다. ' + result.message);
            } else {
                alert('이슈 업데이트에 실패했습니다: ' + result.message); // 수정: 실패 시 서버에서 받은 메시지를 출력
            }
        } catch (error) {
            console.error('이슈 업데이트 실패: ', error);
            alert('이슈 업데이트 중 오류가 발생했습니다.');
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
                    disabled={isEditMode === false}
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
                    disabled={isEditMode === false}
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
                                disabled={isEditMode === false}
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
                    disabled={isEditMode === false}
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
                    disabled={isEditMode === false}
                />
                <label className={styles.label}>Fixer</label>
                <input className={styles.input}
                    type="text"
                    name="fixer"
                    value={issue.fixer}
                    disabled={isEditMode === false}
                />
            </div>
            <div className={styles.btns}>
                <button className={styles.btn} onClick={onCloseIssueClick}>Close Issue</button>
                <button className={styles.btn} onClick={onResolveIssueClick}>Resolve Issue</button>
                <button className={styles.btn} type="submit" onClick={onSaveClick}>Save</button>
            </div>
        </div>
    );
}

export default IssueInfo;