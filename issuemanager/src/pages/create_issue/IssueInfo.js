import React from 'react';
import styles from './CreateIssue.module.css';

function IssueInfo( {issue, setIssue, userId, categories} ) {    
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
                />
                <label className={styles.label}>State</label>
                <input className={styles.input}
                    type="text"
                    name="state"
                    value={issue.state}
                    onChange={handleChange}
                    disabled={userId === 'A'} // A 사용자는 Title 수정 불가
                />
                <label className={styles.label}>Description</label>
                <textarea className={styles.textarea}
                    name="description"
                    value={issue.description}
                    onChange={handleChange}
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
                />
                <label className={styles.label}>Fixer</label>
                <input className={styles.input}
                    type="text"
                    name="fixer"
                    value={issue.fixer}
                    onChange={handleChange}
                />
            </div>
        </div>
    );
}

export default IssueInfo;