// db.js
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
                } else {
                    console.log('Table created successfully');
                }
            });

            // 插入示例数据
            const insertPlant = db.prepare(`
                INSERT INTO ${tableName} (name, category, efficacyScore, distribution, description, usage)
                VALUES (?, ?, ?, ?, ?, ?)
            `);

            insertPlant.run(['丹参', '根茎类', 120, '四川、云南', '活血化瘀', '用于心脑血管疾病']);
            insertPlant.run(['黄芪', '根茎类', 132, '内蒙古、甘肃', '补气固表', '用于体虚乏力']);

            insertPlant.finalize(function(err) {
                if (err) {
                    console.error('Error inserting data:', err.message);
                } else {
                    console.log('Data inserted successfully');
                }
            });
        } else {
            console.log('Table already exists');
        }
    });
});

db.close((err) => {
    if (err) {
        console.error('Error closing database:', err.message);
    } else {
        console.log('Database closed successfully');
    }
});