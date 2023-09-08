let trackIndex = 0;
/*const tracks = [
    {title: 'Track 1', cover: '/images/1.png', src: '/audios/track1.mp3'},
    {title: 'Track 2', cover: '/images/2.png', src: '/audios/track2.mp3'},
    {title: 'Track 3', cover: '/images/3.png', src: '/audios/track3.mp3'},
    {title: 'Track 4', cover: '/images/4.png', src: '/audios/track4.mp3'},
    {title: 'Track 5', cover: '/images/5.png', src: '/audios/track5.mp3'},
];*/

let tracks = [];

async function fetchTracks() {
    try {
        const response = await fetch('/main/top5Music');
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        tracks = await response.json();
        initPlaylist(); // 데이터 로딩 후 플레이리스트 초기화
    } catch (error) {
        console.error('Error fetching the tracks:', error);
    }
}

const audioElement = document.getElementById('audio');
let duration = 0;

function initPlaylist() {
    const playlistDiv = document.querySelector('.playlist');
    tracks.forEach((track, index) => {

        const coverImg = document.createElement('img');
        coverImg.classList.add('ListImage');
        coverImg.src = track.albumImage;
        coverImg.alt = `${track.title} albumImage`;
        coverImg.setAttribute('onerror', "this.src='/pictures/image_not_found.png'");

        coverImg.addEventListener('click', () => selectTrack(index));

        playlistDiv.appendChild(coverImg);

    });
        document.getElementById('currentTrackCover').src = tracks[0].albumImage;
        document.getElementById('currentTrackCover').setAttribute('onerror', "this.src='/pictures/image_not_found.png'");


    audioElement.src = tracks[0].albumMusic;


    // 메타 데이터가 로드되면 트랙의 전체 길이를 얻습니다.
        audioElement.addEventListener('loadedmetadata', () => {
            duration = audioElement.duration;
            document.getElementById('progressSlider').max = duration;
        });

        // 트랙의 재생 위치가 바뀔 때마다 슬라이더의 값을 업데이트합니다.
        audioElement.addEventListener('timeupdate', () => {
            document.getElementById('progressSlider').value = audioElement.currentTime;
        });
}

function changeProgress() {
    const progressSlider = document.getElementById('progressSlider');
    audioElement.currentTime = progressSlider.value;
}

function playPause() {
const playPauseIcon = document.getElementById('playPauseIcon');

    if (audioElement.paused) {
        audioElement.play();
        playPauseIcon.src = "/pictures/sidebar-audio-icon/pause.png";
    } else {
        audioElement.pause();
        playPauseIcon.src = "/pictures/sidebar-audio-icon/play.png";
    }
}

function prevTrack() {
    trackIndex = (trackIndex - 1 + tracks.length) % tracks.length;
    selectTrack(trackIndex);
}

function nextTrack() {
    trackIndex = (trackIndex + 1) % tracks.length;
    selectTrack(trackIndex);
}

function selectTrack(index) {
    trackIndex = index;

    document.getElementById('currentTrackCover').src = tracks[trackIndex].albumImage;
    document.getElementById('currentTrackCover').setAttribute('onerror', "this.src='/pictures/image_not_found.png'");

    audioElement.src = tracks[trackIndex].albumMusic;
    playPauseIcon.src = "/pictures/sidebar-audio-icon/pause.png";
    audioElement.play();
}

function changeVolume() {
    const volumeSlider = document.getElementById('volumeSlider');
    audioElement.volume = volumeSlider.value / 100;
}
document.addEventListener('DOMContentLoaded', fetchTracks);
document.addEventListener('DOMContentLoaded', initPlaylist);

/*음악 재생시간표기 */
audioElement.addEventListener('timeupdate', () => {
    document.getElementById('progressSlider').value = audioElement.currentTime;

    let currentMinutes = Math.floor(audioElement.currentTime / 60);
    let currentSeconds = Math.floor(audioElement.currentTime % 60);

    if(currentSeconds < 10) {
        currentSeconds = '0' + currentSeconds;
    }

    document.getElementById('currentTime').textContent = `${currentMinutes}:${currentSeconds}`;
});