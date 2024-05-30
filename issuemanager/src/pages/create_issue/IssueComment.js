import React from 'react';
import styles from './CreateIssue.module.css';

function IssueComment( {issue, setIssue, userId} ) {
    function handleChange(e) {
        const { name, value } = e.target;
        setIssue((prevIssue) => ({ ...prevIssue, [name]: value }));
    }
    
    function addComment() {
        setIssue((prevIssue) => ({
          ...prevIssue,
          comments: [
            ...prevIssue.comments,
            {
              content: prevIssue.newComment,
              created_at: new Date().toISOString(),
              writer_id: userId, // 현재 사용자 ID 사용
              id: 'id', // 고정된 이슈 ID 또는 동적으로 설정
            }
          ],
          newComment: '',
        }));
    }
    
      function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0'); // getMonth()는 0부터 시작합니다.
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
      
        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }
    
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