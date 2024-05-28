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
    console.log('Issue Edit Mode Toggled');
  };

  useEffect(() => {
    // 컴포넌트가 처음 렌더링될 때 현재 날짜를 설정
    const currentDate = new Date().toISOString().split('T')[0];
    setIssue((prevIssue) => ({ 
      ...prevIssue, 
      reportedDate: currentDate,
     }));
  }, []);

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
          <button className={styles.btn} type="edit" onClick={handleEditClick}>Edit</button>
        </div>
        {/* 이슈 정보 */}
        <IssueInfo
          issue={issue}
          handleChange={handleChange}
          userId={userId}
          categories={categories}
          handleCategoryChange={handleCategoryChange}
          handleSubmit={handleSubmit}
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
          handleChange={handleChange}
          addComment={addComment}
          formatDate={formatDate}
        />
      </div>
    </div>
  );
}

export default EditIssue;