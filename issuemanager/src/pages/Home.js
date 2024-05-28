import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './home.css';  // CSS 파일을 임포트합니다
import Projectinfo from './projectpage';

function Home() {
  const [selectedProject, setSelectedProject] = useState(null); // 선택된 프로젝트 이름 상태

  return (
    <div className="home-container">
      <header className="home-header">
        <h2>IssueManager</h2>
        <Link to="/signin">Sign In</Link>
      </header>
      <main className="home-main">
        <Catalog setSelectedProject={setSelectedProject} />
        <Content selectedProject={selectedProject} />
      </main>
    </div>
  );
}

function Catalog({ setSelectedProject }) {
  const [showInput, setShowInput] = useState(false); // 입력 상자를 보여줄지 여부 상태
  const [projects, setProjects] = useState([]); // 프로젝트 리스트와 setter 선언
  const [newProjectName, setNewProjectName] = useState(''); // 새로운 프로젝트 이름 상태

  const toggleInput = () => {
    setShowInput(!showInput); // 입력 상자를 토글합니다.
    if (!showInput) {
      setNewProjectName(''); // 입력 상자가 나타날 때 새로운 프로젝트 이름 초기화
    }
  };

  const handleInputChange = (event) => {
    setNewProjectName(event.target.value); // 입력 상자의 내용을 업데이트합니다.
  };

  const cancelInput = () => {
    toggleInput(); // 입력 상자를 닫습니다.
  };

  const addProject = () => {
    if (newProjectName.trim() === '') {
      alert('Please enter a project name.'); // 프로젝트 이름이 비어 있으면 알림 표시
      return;
    }
    setProjects([...projects, newProjectName]); // 새로운 프로젝트를 기존 프로젝트 리스트에 추가
    toggleInput(); // 입력 상자를 닫습니다.
  };

  return (
    <aside className="home-catalog">
      <h3>Projects</h3>
      <ul>
        {projects.map((project, index) => (
          <li key={index} className="project-item" onClick={() => setSelectedProject(project)}>
            {project}
          </li>
        ))}
      </ul>
      {showInput && (
        <div className="input-container">
          <div className="input-wrapper">
            <input type="text" value={newProjectName} onChange={handleInputChange} />
            <button className="proj-cancel" onClick={cancelInput}>Cancel</button>
            <button className="proj-add" onClick={addProject}>Add</button>
          </div>
        </div>
      )}
      {!showInput && (
        <button className="add-button" onClick={toggleInput}>Add new Project</button>
      )}
    </aside>
  );
}

function Content({ selectedProject }) {
  return (
    <section className="home-content">
      <Projectinfo project={selectedProject} />
    </section>
  );
}

export default Home;
