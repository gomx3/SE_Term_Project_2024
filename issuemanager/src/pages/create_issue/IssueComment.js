import React, { useState } from 'react';
import styles from './CreateIssue.module.css';

function IssueComment({ user, issue, comment, setComment }) {

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    // API 요청을 보내기 전에, 로컬 상태에 코멘트를 추가하는 방식
    const localComment = {
        id: user.id,
        memberId: user.memberId,
        content: comment.newComment, // 현재 입력 필드에 있는 코멘트 내용
        createdAt: formatDate(new Date().toISOString())
    };

    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}`;
    }

    function handleChange(e) {
        const { name, value } = e.target;
        setComment((prevIssue) => ({ ...prevIssue, [name]: value }));
    }

    async function addComment() {
        const url = `comments/issues/${issue.id}`;

        setLoading(true);
        setError(null);
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    id: user.id,
                    content: comment.newComment,
                }),
            });

            const data = await response.json(); // response.json() 호출 결과를 기다린 후 변수에 할당
            console.log(localComment);

            if (data.isSuccess) {
                setComment((prevIssue) => ({
                    ...prevIssue,
                    comments: [...prevIssue.comments, localComment],
                    newComment: '', // 입력 필드 초기화
                }));
            } else {
                alert(data.message || 'Something went wrong');
            }
        } catch (error) {
            alert('Network error');
        } finally {
            setLoading(false);
        }
    }

    return (
        <div>
            <h3>Add a Comment</h3>
            <textarea
                className={styles.textarea}
                name="newComment"
                value={comment.newComment}
                placeholder='Add your comment here...'
                onChange={handleChange}
                disabled={loading}
            />
            <div className={styles.btns}>
                <button className={styles.btn} onClick={addComment} disabled={loading}>
                    {loading ? 'Submitting...' : 'Comment'}
                </button>
            </div>
            <h4>Comment History</h4>
            {error && <p className={styles.error}>{error}</p>}
            <ul className={styles.commentList}>
                {comment.comments.map((comment) => (
                    <li key={comment.id} className={styles.commentItem}>
                        <div className={styles.commentHeader}>
                            <p className={styles.commentAuthor}>{comment.memberId}</p>
                            <p className={styles.commentDate}>{localComment.createdAt}</p>
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