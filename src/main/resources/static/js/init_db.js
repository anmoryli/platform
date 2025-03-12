// init-db.js
const sqlite3 = require('sqlite3').verbose();
const path = require('path');
const dbPath = path.resolve(__dirname, 'tibetan_medicine.db');
const db = new sqlite3.Database(dbPath);

function checkTableExists(tableName, callback) {
    db.get(`SELECT name FROM sqlite_master WHERE type='table' AND name=?`, [tableName], (err, row) => {
        if (err) {
            console.error('Error checking table existence:', err.message);
            return callback(err);
        }
        callback(null, !!row);
    });
}

db.serialize(() => {
    const tableName = 'MedicinalPlants';

    checkTableExists(tableName, (err, exists) => {
        if (err) {
            console.error('Error checking table existence:', err.message);
            db.close((closeErr) => {
                if (closeErr) {
                    console.error('Error closing database:', closeErr.message);
                } else {
                    console.log('Database closed successfully');
                }
            });
            return;
        }

        if (!exists) {
            // 创建表
            db.run(`
                CREATE TABLE ${tableName} (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    category TEXT,
                    efficacyScore INTEGER,
                    distribution TEXT,
                    description TEXT,
                    usage TEXT
                )
            `, function(err) {
                if (err) {
                    console.error('Error creating table:', err.message);
                    db.close((closeErr) => {
                        if (closeErr) {
                            console.error('Error closing database:', closeErr.message);
                        } else {
                            console.log('Database closed successfully');
                        }
                    });
                    return;
                }

                // 插入示例数据
                const insertPlant = db.prepare(`
                    INSERT INTO ${tableName} (name, category, efficacyScore, distribution, description, usage)
                    VALUES (?, ?, ?, ?, ?, ?)
                `);

                insertPlant.run(['丹参', '根茎类', 120, '四川、云南', '活血化瘀', '用于心脑血管疾病'], function(insertErr) {
                    if (insertErr) {
                        console.error('Error inserting data:', insertErr.message);
                        db.close((closeErr) => {
                            if (closeErr) {
                                console.error('Error closing database:', closeErr.message);
                            } else {
                                console.log('Database closed successfully');
                            }
                        });
                        return;
                    }

                    insertPlant.run(['黄芪', '根茎类', 132, '内蒙古、甘肃', '补气固表', '用于体虚乏力'], function(finalInsertErr) {
                        if (finalInsertErr) {
                            console.error('Error inserting data:', finalInsertErr.message);
                        } else {
                            console.log('Data inserted successfully');
                        }

                        insertPlant.finalize(function(finalizeErr) {
                            if (finalizeErr) {
                                console.error('Error finalizing statement:', finalizeErr.message);
                            }

                            db.close((closeErr) => {
                                if (closeErr) {
                                    console.error('Error closing database:', closeErr.message);
                                } else {
                                    console.log('Database closed successfully');
                                }
                            });
                        });
                    });
                });
            });
        } else {
            console.log('Table already exists');
            db.close((closeErr) => {
                if (closeErr) {
                    console.error('Error closing database:', closeErr.message);
                } else {
                    console.log('Database closed successfully');
                }
            });
        }
    });
});