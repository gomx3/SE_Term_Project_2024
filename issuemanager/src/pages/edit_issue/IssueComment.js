import React from 'react';
import styles from './EditIssue.module.css';

function IssueComment( {issue, handleChange, addComment, formatDate} ) {
    return (
        <div>
            <h3>Add a Comment</h3>
            <textarea className={styles.textarea}
                name="newComment"
                value={issue.newComment}
                placeholder='Add your comment here...'
                onChange={handleChange}
            />
            <div className={styles.btns}>
                <button className={styles.btn} onClick={addComment}>Comment</button>
            </div>
            <h4>comment history: </h4>
            <ul className={styles.commentList}>
                {issue.comments.map((comment) => (
                <li key={comment.id} className={styles.commentItem}>
                    <div className={styles.commentHeader}>
                    <p className={styles.commentAuthor}>{comment.writer_id}</p>
                    <p className={styles.commentDate}>{formatDate(comment.created_at)}</p>
                    </div>
                    <hr className={styles.commentDivider} />
                    <div className={styles.commentContent}>
                    <p>{comment.content}</p>
                    </div>
                </li>
                ))}
            </ul>
        </div>
    );
}

export default IssueComment;