import React from 'react';
import styles from './EditIssue.module.css';

function IssueInfo( {issue, setIssue, userId, categories, isEditMode} ) {
    
    /* 임시 */
    const devList = ["seoyeon_dev", "seoyeon22", "user123"];
    const RecommendedReporterString = devList.join(', ');

    function handleChange(e) {
        const { name, value } = e.target;
        setIssue((prevIssue) => ({ ...prevIssue, [name]: value }));
    }
    
    function handleCategoryChange(category) {
        setIssue((prevIssue) => {
            if (prevIssue.category.includes(category)) {
                return {
                ...prevIssue,
                category: prevIssue.category.filter((item) => item !== category),
                };
            } else {
                return {
                ...prevIssue,
                category: [...prevIssue.category, category],
                };
            }
        });
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
                    disabled={isEditMode === false} // 편집 버튼 비활성화시 편집 불가
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
                    {categories.map((category) => (
                    <div key={category} className={styles.categoryContainer}>
                        <input className={styles.input}
                        type="radio"
                        id={category}
                        name="category"
                        value={category}
                        checked={issue.category.includes(category)}
                        onChange={() => handleCategoryChange(category)}
                        disabled={isEditMode === false}
                        />
                        <label className={styles.label} htmlFor={category}>{category}</label>
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
                    onChange={handleChange}
                    disabled={isEditMode === false}
                />
                <div className={styles.recommendContainer}>
                    <label className={styles.recommendlabel}>Recommended reporter: {RecommendedReporterString}</label>
                </div>
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
                    <option value="major">Major</option>
                    <option value="blocker">Blocker</option>
                    <option value="critical">Critical</option>
                    <option value="minor">Minor</option>
                    <option value="trivial">Trivial</option>
                </select>
                <label className={styles.label}>Assignee</label>
                <input className={styles.input}
                    type="text"
                    name="assignee"
                    value={issue.assignee}
                    onChange={handleChange}
                    disabled={isEditMode === false}
                />
                <label className={styles.label}>Fixer</label>
                <input className={styles.input}
                    type="text"
                    name="fixer"
                    value={issue.fixer}
                    onChange={handleChange}
                    disabled={isEditMode === false}
                />
            </div>
        </div>
    );
}

export default IssueInfo;