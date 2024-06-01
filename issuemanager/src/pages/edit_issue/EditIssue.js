import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import IssueInfo from './IssueInfo';
import IssueComment from './IssueComment';
import styles from './EditIssue.module.css';

function EditIssue() {
  /* 프로젝트 정보 */
  const projectId = '2';
  /* 사용자 정보 */
  const [user, setUser] = useState({ // 로그인한 사용자
    id: 6,
    memberId: 'seoyeon3',
    role: 'PL',
  });
  /* 이슈 정보  */
  const [issue, setIssue] = useState({
    id: 79,
    title: 'mytitle',
    description: 'nononono',
    status: 'NEW',
    category: 'OTHERS',
    reporter: 'tt',
    reportedDate: '2024-05-31 04:14:37.409805',
    priority: 'BLOCKER',
    assignee: '',
    fixer: '',
  });
  /* 코멘트 정보 */
  const [comment, setComment] = useState({
    comments: [],
    newComment: '',
  })
  /* 편집 모드 관리 */
  const [isEditMode, setIsEditMode] = useState(false);
  /* 카테고리 리스트 */
  const categories = [
    'MEMORY_LEAK',
    'CRASH',
    'USER_FEEDBACK',
    'SECURITY',
    'OTHERS',
  ]

  /* 이슈 삭제 함수 */
  const navigate = useNavigate();
  async function handleDelete() {
    try {
      const response = await fetch(`/issues/${issue.id}?memberId=${user.id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          memberId: user.id,
        }),
      });
  
      const data = await response.json();
  
      if (data.isSuccess) {
        alert('이슈가 성공적으로 삭제되었습니다. ' + data.message);
        navigate('/'); // 삭제 성공하면 홈으로 이동
      } else {
        throw new Error(data.message || 'Failed to delete the issue.');
      }
    } catch (error) {
      console.error('Error occurred while deleting the issue:', error);
    }
  };

  function handleEditClick() {
    setIsEditMode((prevMode) => !prevMode);
    console.log('Issue Edit Mode Toggled', isEditMode);
  };

  return (
    <div className={styles.container}>
      <div className={styles.editContainer}>
        {/* 페이지 타이틀 */}
        <div className={styles.pageTitleContainer}>
          {/*<h1 className={styles.h1}>{isEditMode ? 'ISSUE EDIT' : 'ISSUE DETAILS'}</h1>*/}
          <h1 className={styles.h1}>ISSUE DETAILS</h1>
          <div className={styles.btnIssues}>
            <button
              className={styles.btnDelete}
              onClick={handleDelete}>
              Delete
            </button>
            <button
              className={styles.btnEditMode}
              type={isEditMode ? "submit" : null}
              onClick={handleEditClick}>
              Edit
            </button>
          </div>
        </div>
        {/* 이슈 정보 */}
        <IssueInfo
          projectId={projectId}
          user={user}
          issue={issue}
          setIssue={setIssue}
          categories={categories}
          isEditMode={isEditMode}
        />
      </div>
      {/* 이슈 코멘트 */}
      <div className={styles.commentContainer}>
        {/* 현재 사용자 memberId 출력 */}
        <div className={styles.userInfo}>
          <p>current user: {user.memberId}</p>
        </div>
        <IssueComment
          user={user}
          issue={issue}
          comment={comment}
          setComment={setComment}
        />
      </div>
    </div>
  );
}

export default EditIssue;