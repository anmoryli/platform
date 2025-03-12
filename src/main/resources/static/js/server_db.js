// server.js
const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const sqlite3 = require('sqlite3').verbose();

const app = express();
const PORT = 3001;

app.use(cors());
app.use(bodyParser.json());

const db = new sqlite3.Database('./tibetan_medicine.db');

// 获取所有植物
app.get('/api/plants', (req, res) => {
    db.all('SELECT * FROM MedicinalPlants', [], (err, rows) => {
        if (err) {
            return res.status(500).json({ error: err.message });
        }
        res.json(rows);
    });
});

// 添加新植物
app.post('/api/plants', (req, res) => {
    const { name, category, efficacyScore, distribution, description, usage } = req.body;
    db.run(
        'INSERT INTO MedicinalPlants (name, category, efficacyScore, distribution, description, usage) VALUES (?, ?, ?, ?, ?, ?)',
        [name, category, efficacyScore, distribution, description, usage],
        function(err) {
            if (err) {
                return res.status(500).json({ error: err.message });
            }
            res.json({
                id: this.lastID,
                name,
                category,
                efficacyScore,
                distribution,
                description,
                usage
            });
        }
    );
});

app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});