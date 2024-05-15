import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styles from './Body.module.css';

function Body() {
    return (
        <div className={styles.container}>
            <div className={styles.sidebar_container}>
                <h2>This is Body</h2>
            </div>
            <div className={styles.sidebar_container}>
                <h2>issue1</h2>
            </div>
            <div className={styles.sidebar_container}>
                <h2>issue2</h2>
            </div>
            <div className={styles.sidebar_container}>
                <h2>issue3</h2>
            </div>
        </div>
    );
}

export default Body