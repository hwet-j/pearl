const dropArrow = document.getElementById('dropArrow');
const playlist = document.getElementById('playlist');

dropArrow.addEventListener('click', () => {
    playlist.classList.toggle('show');
});