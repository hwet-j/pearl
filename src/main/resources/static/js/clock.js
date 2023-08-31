function updateDday(targetDate) {
    const ddayElement = document.getElementById('dday');
    const now = new Date();
    const targetDateTime = new Date(targetDate);

    const timeDiff = targetDateTime - now;

    if (timeDiff <= 0) {
        ddayElement.textContent = "D-Day has arrived!";
    } else {
        const days = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
        const hours = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((timeDiff % (1000 * 60)) / 1000);

        ddayElement.textContent = `D-${days} ${hours}h ${minutes}m ${seconds}s`;
    }
}

function startCountdown(targetDate) {
    updateDday(targetDate); // 초기값 설정

    setInterval(function() {
        updateDday(targetDate);
    }, 1000);
}

window.addEventListener('load', function() {
    const targetDateElement = document.getElementById('targetDate');
    const targetDate = targetDateElement.getAttribute('data-targetDate');

    startCountdown(targetDate);
});
