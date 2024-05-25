import React, { useState, useEffect } from 'react';
import './EditIssue.css';

function EditIssue() {
  const [issue, setIssue] = useState({
    title: '',
    description: '',
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
    // 이슈 편집 완료 처리
    console.log('Issue edited:', issue);
  }

  const categories = [
    'Memory Leak',
    'Crash Occurrence',
    'User Feedback',
    'Security Vulnerability',
    'Others',
  ]

  return (
    <div className="container">
      <div className="edit-container">
        <div>
          <h1>EDIT ISSUE</h1>
        </div>
        <div className="info-container">
          <label>Title</label>
          <input
            type="text"
            name="title"
            value={issue.title}
            onChange={handleChange}
            disabled={userId === 'tester'} // A 사용자는 Title 수정 불가
          />
          <label>Description</label>
          <textarea
            name="description"
            value={issue.description}
            onChange={handleChange}
          />
          <label>Issue Category</label>
          <div>
            {categories.map((category) => (
              <div key={category} className="category-container">
                <input
                  type="radio"
                  id={category}
                  name="category"
                  value={category}
                  checked={issue.category.includes(category)}
                  onChange={() => handleCategoryChange(category)}
                />
                <label htmlFor={category}>{category}</label>
              </div>
            ))}
          </div>
        </div>
        <div className="info-container">
          <label>Reporter</label>
          <input
            type="text"
            name="reporter"
            value={issue.reporter}
            onChange={handleChange}
          />
          <label>Reported Date</label>
          <input
            type="text"
            name="reportedDate"
            value={issue.reportedDate}
            disabled // 날짜는 수정 불가능
          />
          <label>Priority</label>
          <select
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
          <label>Assignee</label>
          <input
            type="text"
            name="assignee"
            value={issue.assignee}
            onChange={handleChange}
          />
          <label>Fixer</label>
          <input
            type="text"
            name="fixer"
            value={issue.fixer}
            onChange={handleChange}
          />
          <div className='button-container'>
            <button onClick={() => console.log('Issue closed')}>Close Issue</button>
            <button onClick={() => console.log('Issue resolved')}>Resolve Issue</button>
            <button type="submit" onClick={handleSubmit}>Edit</button>
          </div>
        </div>
      </div>
      <div>
        <div className="comments-container">
          <h3>Add a Comment</h3>
          <textarea
            name="newComment"
            value={issue.newComment}
            placeholder='Add your comment here...'
            onChange={handleChange}
          />
          <div className='button-container'>
            <button onClick={addComment}>Comment</button>
          </div>
          <h4>comment history: </h4>
          <ul className="comment-list">
            {issue.comments.map((comment) => (
              <li key={comment.id} className="comment-item">
                <div className="comment-header">
                  <p className="comment-author">{comment.writer_id}</p>
                  <p className="comment-date">{formatDate(comment.created_at)}</p>
                </div>
                <hr className="comment-divider" />
                <div className="comment-content">
                  <p>{comment.content}</p>
                </div>
              </li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
}

export default EditIssue;