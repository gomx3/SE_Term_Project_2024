import React from 'react';
import { Link } from 'react-router-dom';
import Header from './components/Header';
import Sidebar from './components/Sidebar';
import Body from './components/Body';
import styles from './Home.module.css';

function Home() {
  return (
    <section className={styles.home}>
      <Header />
      <div className={styles.container}>
        <Sidebar />
        <Body />
      </div>
    </section>
  );
}

export default Home;