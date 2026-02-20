document.addEventListener('DOMContentLoaded', () => {
    fetchProblems();
    fetchProfile();
    initNavigation();
    initVisualizer();

    const modal = document.getElementById('problem-modal');
    const closeBtn = document.querySelector('.close-modal');

    if (closeBtn) {
        closeBtn.onclick = () => {
            modal.style.display = 'none';
        };
    }

    window.onclick = (event) => {
        if (event.target == modal) {
            modal.style.display = 'none';
        }
    };
});

// Navigation Logic
function initNavigation() {
    const navBtns = document.querySelectorAll('.nav-btn');
    const pages = document.querySelectorAll('.page');

    navBtns.forEach(btn => {
        btn.addEventListener('click', (e) => {
            e.preventDefault();
            const target = btn.getAttribute('data-target');

            navBtns.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');

            pages.forEach(p => p.classList.remove('active'));
            const targetPage = document.getElementById(target);
            if (targetPage) targetPage.classList.add('active');
        });
    });
}

// Profile Logic
async function fetchProfile() {
    try {
        const response = await fetch('/api/profile');
        const user = await response.json();

        const welcome = document.getElementById('user-welcome');
        const name = document.getElementById('profile-name');
        const solved = document.getElementById('profile-solved');
        const time = document.getElementById('profile-time');

        if (welcome) welcome.textContent = user.name;
        if (name) name.textContent = user.name;
        if (solved) solved.textContent = user.totalProblemsSolved;
        if (time) time.textContent = user.totalTimeSpent + 'ms';
    } catch (error) {
        console.error('Error fetching profile:', error);
    }
}

// Problems Logic
async function fetchProblems() {
    try {
        const response = await fetch('/api/problems');
        const data = await response.json();

        renderProblems('system-problems', data.systemProblems, 'system');
        renderProblems('custom-problems', data.customProblems, 'custom');

        const systemCount = document.getElementById('system-count');
        const customCount = document.getElementById('custom-count');

        if (systemCount) systemCount.textContent = data.systemProblems.length;
        if (customCount) customCount.textContent = data.customProblems.length;
    } catch (error) {
        console.error('Error fetching problems:', error);
    }
}

function renderProblems(containerId, problems, type) {
    const container = document.getElementById(containerId);
    if (!container) return;
    container.innerHTML = '';

    if (!problems || problems.length === 0) {
        container.innerHTML = '<p class="empty-msg">No problems found.</p>';
        return;
    }

    problems.forEach(problem => {
        const card = document.createElement('div');
        card.className = 'problem-card';

        const title = type === 'system' ? problem.title : problem.name;
        const topic = type === 'system' ? 'System Algorithm' : problem.topic;

        card.innerHTML = `
            <span class="topic">${topic}</span>
            <h3>${title}</h3>
            <p>${problem.description}</p>
        `;

        card.addEventListener('click', () => showProblemDetails(problem, type));
        container.appendChild(card);
    });
}

function showProblemDetails(problem, type) {
    const modal = document.getElementById('problem-modal');
    const modalBody = document.getElementById('modal-body');
    if (!modal || !modalBody) return;

    const title = type === 'system' ? problem.title : problem.name;
    const topic = type === 'system' ? 'System' : problem.topic;

    modalBody.innerHTML = `
        <h2>${title}</h2>
        <div class="meta">
            <span><strong>Topic:</strong> ${topic}</span>
            <span><strong>Source:</strong> ${type}</span>
        </div>
        <div class="modal-description">
            <p>${problem.description}</p>
            ${problem.explanation ? `<h3>Explanation</h3><p>${problem.explanation}</p>` : ''}
            ${problem.inputFormat ? `<h3>Input Format</h3><p>${problem.inputFormat}</p>` : ''}
            ${problem.outputFormat ? `<h3>Output Format</h3><p>${problem.outputFormat}</p>` : ''}
        </div>
    `;

    modal.style.display = 'block';
}

// Visualizer Logic
let array = [];
let arrayContainer, indicatorsContainer, statusText, floatingExplanation, codeDisplay;

const ALGO_CODE = {
    bubble: [
        "for (let i = 0; i < n - 1; i++) {",
        "  for (let j = 0; j < n - i - 1; j++) {",
        "    if (arr[j] > arr[j + 1]) {",
        "      swap(arr[j], arr[j + 1]);",
        "    }",
        "  }",
        "}"
    ],
    selection: [
        "for (let i = 0; i < n; i++) {",
        "  let minIdx = i;",
        "  for (let j = i + 1; j < n; j++) {",
        "    if (arr[j] < arr[minIdx]) {",
        "      minIdx = j;",
        "    }",
        "  }",
        "  swap(arr[i], arr[minIdx]);",
        "}"
    ],
    insertion: [
        "for (let i = 1; i < n; i++) {",
        "  let key = arr[i];",
        "  let j = i - 1;",
        "  while (j >= 0 && arr[j] > key) {",
        "    arr[j + 1] = arr[j];",
        "    j--;",
        "  }",
        "  arr[j + 1] = key;",
        "}"
    ]
};

function initVisualizer() {
    arrayContainer = document.getElementById('array-container');
    indicatorsContainer = document.getElementById('indicators-container');
    statusText = document.getElementById('visualizer-status');
    floatingExplanation = document.getElementById('floating-explanation');
    codeDisplay = document.getElementById('code-display');

    if (!arrayContainer) return;

    generateNewArray();
    const startBtn = document.getElementById('start-btn');
    const resetBtn = document.getElementById('reset-btn');
    const algoSelect = document.getElementById('algo-select');

    if (startBtn) startBtn.addEventListener('click', startVisualization);
    if (resetBtn) resetBtn.addEventListener('click', generateNewArray);
    if (algoSelect) {
        algoSelect.addEventListener('change', () => loadCodeSnippet(algoSelect.value));
        loadCodeSnippet(algoSelect.value); // Initial load
    }
}

function loadCodeSnippet(algo) {
    if (!codeDisplay) return;
    const code = ALGO_CODE[algo];
    codeDisplay.innerHTML = code.map((line, idx) => `<div class="code-line" id="line-${idx}">${line}</div>`).join('');
}

function highlightLine(lineIdx) {
    document.querySelectorAll('.code-line').forEach(el => el.classList.remove('active'));
    const line = document.getElementById(`line-${lineIdx}`);
    if (line) line.classList.add('active');
}

function generateNewArray() {
    array = [];
    if (arrayContainer) arrayContainer.innerHTML = '';
    if (indicatorsContainer) indicatorsContainer.innerHTML = '';
    if (floatingExplanation) floatingExplanation.classList.remove('active');

    for (let i = 0; i < 10; i++) {
        const val = Math.floor(Math.random() * 200) + 50;
        array.push(val);
        const bar = document.createElement('div');
        bar.style.height = `${val}px`;
        bar.className = 'array-bar';
        bar.id = `bar-${i}`;
        bar.textContent = val;
        if (arrayContainer) arrayContainer.appendChild(bar);
    }
    if (statusText) statusText.textContent = 'Random array generated. Choose an algorithm!';
}

function updateExplanation(text, indices, color = 'var(--primary)') {
    if (!floatingExplanation) return;
    floatingExplanation.innerHTML = text;
    floatingExplanation.classList.add('active');
    floatingExplanation.style.backgroundColor = color;

    if (indices && indices.length > 0) {
        const firstBar = document.getElementById(`bar-${indices[0]}`);
        const lastBar = document.getElementById(`bar-${indices[indices.length - 1]}`);

        if (!firstBar || !lastBar) return;

        const containerRect = arrayContainer.getBoundingClientRect();
        const firstRect = firstBar.getBoundingClientRect();
        const lastRect = lastBar.getBoundingClientRect();

        const left = (firstRect.left + lastRect.right) / 2 - containerRect.left - (floatingExplanation.offsetWidth / 2);
        const maxHeight = Math.max(firstRect.height, lastRect.height);

        floatingExplanation.style.left = `${Math.max(10, Math.min(left, containerRect.width - floatingExplanation.offsetWidth - 10))}px`;
        floatingExplanation.style.bottom = `${maxHeight + 100}px`;
    }
}

function setMarkers(markers) {
    if (!indicatorsContainer || !arrayContainer) return;
    indicatorsContainer.innerHTML = '';

    markers.forEach(m => {
        const bar = document.getElementById(`bar-${m.idx}`);
        if (!bar) return;

        const marker = document.createElement('div');
        marker.className = 'indicator-marker';

        marker.innerHTML = `
            <div class="marker-label" style="background: ${m.color || 'var(--primary)'}">${m.label}</div>
            <div class="arrow" style="color: ${m.color || 'var(--primary)'}"></div>
        `;

        const barRect = bar.getBoundingClientRect();
        const containerRect = arrayContainer.getBoundingClientRect();
        const left = barRect.left - containerRect.left + (barRect.width / 2) - 10;

        marker.style.left = `${left}px`;
        marker.style.bottom = `${barRect.height + 10}px`;
        indicatorsContainer.appendChild(marker);
    });
}

async function startVisualization() {
    const algoSelect = document.getElementById('algo-select');
    if (!algoSelect) return;
    const algo = algoSelect.value;
    const bars = document.querySelectorAll('.array-bar');
    bars.forEach(b => b.classList.remove('sorted'));

    if (algo === 'bubble') {
        await bubbleSort(bars);
    } else if (algo === 'selection') {
        await selectionSort(bars);
    } else if (algo === 'insertion') {
        await insertionSort(bars);
    }

    if (indicatorsContainer) indicatorsContainer.innerHTML = '';
    if (floatingExplanation) floatingExplanation.classList.remove('active');
    highlightLine(-1);
}

async function bubbleSort(bars) {
    if (statusText) statusText.textContent = 'Running Bubble Sort...';
    const delay = 1500;

    for (let i = 0; i < array.length - 1; i++) {
        highlightLine(0);
        await sleep(500);
        for (let j = 0; j < array.length - i - 1; j++) {
            highlightLine(1);
            bars[j].classList.add('comparing');
            bars[j + 1].classList.add('comparing');
            setMarkers([{ idx: j, label: 'j', color: '#f59e0b' }, { idx: j + 1, label: 'j+1', color: '#f59e0b' }]);

            updateExplanation(`Comparing elements`, [j, j + 1], '#f59e0b');
            await sleep(delay);

            highlightLine(2);
            await sleep(delay / 2);
            if (array[j] > array[j + 1]) {
                highlightLine(3);
                updateExplanation(`Swap: ${array[j]} > ${array[j + 1]}`, [j, j + 1], '#ef4444');
                setMarkers([{ idx: j, label: 'swap', color: '#ef4444' }, { idx: j + 1, label: 'swap', color: '#ef4444' }]);

                [array[j], array[j + 1]] = [array[j + 1], array[j]];
                bars[j].style.height = `${array[j]}px`;
                bars[j].textContent = array[j];
                bars[j + 1].style.height = `${array[j + 1]}px`;
                bars[j + 1].textContent = array[j + 1];
                await sleep(delay);
            } else {
                updateExplanation(`Keep: ${array[j]} ≤ ${array[j + 1]}`, [j, j + 1], '#10b981');
                await sleep(delay);
            }

            bars[j].classList.remove('comparing');
            bars[j + 1].classList.remove('comparing');
        }
        bars[array.length - i - 1].classList.add('sorted');
    }
    bars[0].classList.add('sorted');
    if (statusText) statusText.textContent = '✅ Bubble Sort Completed!';
}

async function selectionSort(bars) {
    if (statusText) statusText.textContent = 'Running Selection Sort...';
    const delay = 1500;

    for (let i = 0; i < array.length; i++) {
        highlightLine(0);
        let minIdx = i;
        await sleep(delay / 2);
        highlightLine(1);
        updateExplanation(`Setting current min`, [i]);
        bars[i].classList.add('comparing');
        setMarkers([{ idx: i, label: 'i/min', color: 'var(--primary)' }]);
        await sleep(delay);

        for (let j = i + 1; j < array.length; j++) {
            highlightLine(2);
            bars[j].classList.add('comparing');
            setMarkers([{ idx: i, label: 'i', color: 'var(--primary)' }, { idx: minIdx, label: 'min', color: '#ef4444' }, { idx: j, label: 'j', color: '#f59e0b' }]);
            updateExplanation(`Check if ${array[j]} < ${array[minIdx]}`, [minIdx, j], '#f59e0b');
            await sleep(delay);

            highlightLine(3);
            await sleep(delay / 2);
            if (array[j] < array[minIdx]) {
                highlightLine(4);
                updateExplanation(`Yes! New min found`, [j], '#ef4444');
                if (minIdx !== i) bars[minIdx].classList.remove('comparing');
                minIdx = j;
                setMarkers([{ idx: i, label: 'i', color: 'var(--primary)' }, { idx: minIdx, label: 'min', color: '#ef4444' }, { idx: j, label: 'j', color: '#f59e0b' }]);
                await sleep(delay);
            } else {
                bars[j].classList.remove('comparing');
            }
        }

        highlightLine(7);
        if (minIdx !== i) {
            updateExplanation(`Swap i with min`, [i, minIdx], '#ef4444');
            setMarkers([{ idx: i, label: 'swap', color: '#ef4444' }, { idx: minIdx, label: 'swap', color: '#ef4444' }]);
            [array[i], array[minIdx]] = [array[minIdx], array[i]];
            bars[i].style.height = `${array[i]}px`;
            bars[i].textContent = array[i];
            bars[minIdx].style.height = `${array[minIdx]}px`;
            bars[minIdx].textContent = array[minIdx];
            await sleep(delay);
        }

        bars[minIdx].classList.remove('comparing');
        bars[i].classList.remove('comparing');
        bars[i].classList.add('sorted');
    }
    if (statusText) statusText.textContent = '✅ Selection Sort Completed!';
}

async function insertionSort(bars) {
    if (statusText) statusText.textContent = 'Running Insertion Sort...';
    const delay = 1500;

    bars[0].classList.add('sorted');
    for (let i = 1; i < array.length; i++) {
        highlightLine(0);
        let key = array[i];
        let j = i - 1;
        highlightLine(1);
        await sleep(delay / 3);
        highlightLine(2);
        updateExplanation(`Picking ${key}`, [i]);
        bars[i].classList.add('comparing');
        setMarkers([{ idx: i, label: 'key', color: '#6366f1' }, { idx: j, label: 'j', color: '#f59e0b' }]);
        await sleep(delay);

        highlightLine(3);
        while (j >= 0 && array[j] > key) {
            updateExplanation(`Shift right`, [j, j + 1], '#f59e0b');
            bars[j].classList.add('comparing');
            setMarkers([{ idx: i, label: 'key', color: '#6366f1' }, { idx: j, label: 'j', color: '#f59e0b' }, { idx: j + 1, label: 'j+1', color: '#ef4444' }]);
            await sleep(delay);

            highlightLine(4);
            array[j + 1] = array[j];
            bars[j + 1].style.height = `${array[j + 1]}px`;
            bars[j + 1].textContent = array[j + 1];
            await sleep(delay);

            highlightLine(5);
            bars[j].classList.remove('comparing');
            j = j - 1;
            await sleep(delay / 2);
            highlightLine(3);
        }

        highlightLine(7);
        updateExplanation(`Insert ${key}`, [j + 1], '#10b981');
        array[j + 1] = key;
        bars[j + 1].style.height = `${array[j + 1]}px`;
        bars[j + 1].textContent = array[j + 1];
        setMarkers([{ idx: j + 1, label: 'inserted', color: '#10b981' }]);

        bars[i].classList.remove('comparing');
        for (let k = 0; k <= i; k++) bars[k].classList.add('sorted');
        await sleep(delay);
    }
    if (statusText) statusText.textContent = '✅ Insertion Sort Completed!';
}

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
