import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styles from './Body.module.css';

function Body() {
    return (
        <div className={styles.container}>
            <div className={styles.body_container}>
                <h2>This is Body</h2>
            </div>
            <div className={styles.body_container}>
                <div>issue1</div>
            </div>
            <div className={styles.body_container}>
                <div>issue2</div>
            </div>
            <div className={styles.body_container}>
                <div>issue3</div>
            </div>
        </div>
    );
}

export default Body