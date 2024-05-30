import React, { useState, useEffect } from 'react';
import IssueInfo from './IssueInfo';
import IssueComment from './IssueComment';
import styles from './EditIssue.module.css';

function EditIssue() {
  const [issue, setIssue] = useState({
    title: '',
    description: '',
    state: '',
    category: '',
    reporter: '',
    reportedDate: '',
    priority: '',
    assignee: '',
    fixer:'',
    comments: [],
    newComment: '',
  });
  const [userId, setUserId] = useState('tester'); // 현재 사용자 id (A 또는 B, ...)
  const [isEditMode, setIsEditMode] = useState(false);
  const categories = [
    'Memory Leak',
    'Crash Occurrence',
    'User Feedback',
    'Security Vulnerability',
    'Others',
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
          issue={issue}
          setIssue={setIssue}
          userId={userId}
          categories={categories}
          isEditMode={isEditMode}
        />
        <div className={styles.btns}>
                <button className={styles.btn} onClick={() => console.log('Issue closed')}>Close Issue</button>
                <button className={styles.btn} onClick={() => console.log('Issue resolved')}>Resolve Issue</button>
                <button className={styles.btn} type="submit" onClick={handleSubmit}>Save</button>
        </div>
      </div>
      {/* 이슈 코멘트 */}
      <div className={styles.commentContainer}>
        <IssueComment
          issue={issue}
          setIssue={setIssue}
          userId={userId}
        />
      </div>
    </div>
  );
}

export default EditIssue;