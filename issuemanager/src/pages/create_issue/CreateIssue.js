import React, { useState, useEffect } from 'react';
import { useLocation } from "react-router-dom";
import IssueInfo from './IssueInfo';
import IssueComment from './IssueComment';
import styles from './CreateIssue.module.css';

function CreateIssue() {
  const location = useLocation(); // 프로젝트 페이지에서 정보 받아오기
  
  /* 프로젝트 정보 */
  const projectId = location.state.projectId;
  /* 사용자 정보 */
  const [user, setUser] = useState({ // 로그인한 사용자로 설정하게
    id: location.state.userId,
    memberId: location.state.memberId,
    role: location.state.userRole,
  });
  /* 이슈 정보  */
  const [issue, setIssue] = useState({
    id: '',
    title: '',
    description: '',
    status: 'NEW',
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
    content: '',
  })
  
  const categories = [
    'MEMORY_LEAK',
    'CRASH',
    'USER_FEEDBACK',
    'SECURITY',
    'OTHERS',
  ]

  function getLocalDateISOString() {
    const now = new Date();
    const timezoneOffset = now.getTimezoneOffset() * 60000; // UTC를 KST로 (로컬 시간대의 오프셋을 분으로 계산)
    // 현재 시간에서 시간대 오프셋을 빼서 로컬 시간을 UTC와 동일한 형식으로 맞춤
    const localISOString = new Date(now - timezoneOffset).toISOString().split('T')[0];
    return localISOString;
  }

  useEffect(() => {
    const currentDate = getLocalDateISOString();
    setIssue((prevIssue) => ({
      ...prevIssue,
      reportedDate: currentDate,
    }));
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.editContainer}>
        <div>
          <h1 className={styles.h1}>CREATE ISSUE</h1>
        </div>
        <IssueInfo
          projectId={projectId}
          user={user}
          issue={issue}
          setIssue={setIssue}
          categories={categories}
        />
      </div>
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

export default CreateIssue;