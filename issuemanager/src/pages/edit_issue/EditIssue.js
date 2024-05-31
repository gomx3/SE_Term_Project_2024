import React, { useState, useEffect } from 'react';
import IssueInfo from './IssueInfo';
import IssueComment from './IssueComment';
import styles from './EditIssue.module.css';

function EditIssue() {
  /* 프로젝트 정보 */
  const projectId = `2`;
  /* 사용자 정보 */
  const [user, setUser] = useState({
    id: 80,
    memberId: 'tt', // 로그인한 사용자로 설정하게
    role: 'TESTER',
  });
  /* 이슈 정보  */
  const [issue, setIssue] = useState({
    id: 31,
    title: '',
    description: '',
    state: 'NEW',
    category: 'OTHERS',
    reporter: user.memberId, // 로그인한 사용자
    reportedDate: '',
    priority: 'MAJOR',
    assignee: '',
    fixer:'',
  });
  /* 코멘트 정보 */
  const [comment, setComment] = useState({
    comments: [],
    newComment: '',
  })
  /* 편집 모드 관리 */
  const [isEditMode, setIsEditMode] = useState(false);
  
  const categories = [
    'MEMORY_LEAK',
    'CRASH',
    'USER_FEEDBACK',
    'SECURITY',
    'OTHERS',
  ]

  const handleEditClick = () => {
    setIsEditMode((prevMode) => !prevMode);
    console.log('Issue Edit Mode Toggled', isEditMode);
  };

  useEffect(() => {
    // 컴포넌트가 처음 렌더링될 때 현재 날짜를 설정
    const currentDate = new Date().toISOString().split('T')[0];
    setIssue((prevIssue) => ({ 
      ...prevIssue, 
      reportedDate: currentDate,
     }));
  }, []);

  function handleSubmit() {
    console.log('Issue edited: ', issue);
  }

  return (
    <div className={styles.container}>
      <div className={styles.editContainer}>
        {/* 페이지 타이틀 */}
        <div className={styles.pageTitleContainer}>
          {/*<h1 className={styles.h1}>{isEditMode ? 'ISSUE EDIT' : 'ISSUE DETAILS'}</h1>*/}
          <h1 className={styles.h1}>ISSUE DETAILS</h1>
          <button
            className={isEditMode ? styles.btnEditMode : styles.btnNotEditMode}
            type="button"
            onClick={handleEditClick}>
            Edit
          </button>
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