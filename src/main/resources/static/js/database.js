document.addEventListener('DOMContentLoaded', () => {
    fetchPlants();
});

async function fetchPlants() {
    try {
        const response = await fetch('http://localhost:3001/api/plants');
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const plants = await response.json();

        const container = document.getElementById('plant-cards');
        container.innerHTML = '';

        plants.forEach(plant => {
            const card = document.createElement('div');
            card.classList.add(
                'bg-white',
                'rounded-lg',
                'shadow-md',
                'p-6',
                'transition',
                'transform',
                'hover:scale-105',
                'duration-300',
                'ease-in-out'
            );

            card.innerHTML = `
                <h2 class="text-xl font-semibold">${plant.name}</h2>
                <p class="text-gray-600">${plant.category}</p>
                <p>药效评分: ${plant.efficacyScore}</p>
                <p>分布: ${plant.distribution}</p>
                <p>描述: ${plant.description}</p>
                <p>用途: ${plant.usage}</p>
            `;

            container.appendChild(card);
        });
    } catch (error) {
        console.error('Error fetching plants:', error);
    }
}

document.getElementById('add-plant-form').addEventListener('submit', async (event) => {
    event.preventDefault();

    const formData = {
        name: document.getElementById('name').value,
        category: document.getElementById('category').value,
        efficacyScore: parseInt(document.getElementById('efficacyScore').value),
        distribution: document.getElementById('distribution').value,
        description: document.getElementById('description').value,
        usage: document.getElementById('usage').value,
    };

    try {
        const response = await fetch('http://localhost:3001/api/plants', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData),
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const result = await response.json();
        console.log('Plant added successfully:', result);

        // Refresh the plant cards
        fetchPlants();
    } catch (error) {
        console.error('Error adding plant:', error);
    }
});