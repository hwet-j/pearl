const container = document.querySelector('.scroller');
let page = 2; // 시작 페이지 번호 (2부터 시작)
let loading = false;
const loadingIndicator = document.getElementById('loadingIndicator');

function fetchMoreData() {
    if (loading) return; // 이미 로딩 중이라면 중복 호출 방지
    loading = true; // 로딩 시작
    loadingIndicator.style.display = 'block'; // 로딩 중 엘리먼트 표시

    setTimeout(() => {
        fetch(`/testing/getMore/${page}`)
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    data.forEach(musicAuction => {
                        const card = document.createElement('div');
                        card.classList.add('card');

                        const albumImage = document.createElement('div');
                        albumImage.classList.add('albumImage');
                        const image = document.createElement('img');
                        image.setAttribute('src', musicAuction.albumImage);
                        image.onerror = function() {
                            this.src = '/pictures/image_not_found.png';
                        };
                        albumImage.appendChild(image);

                        const top = document.createElement('div');
                        top.textContent = musicAuction.id;

                        const bottom = document.createElement('div');
                        bottom.textContent = musicAuction.authorNickname.nickname;

                        card.appendChild(albumImage);
                        card.appendChild(top);
                        card.appendChild(bottom);

                        card.addEventListener('click', () => {
                                window.location.href = '/detail?id=' + musicAuction.id;
                            });

                        container.appendChild(card);
                    });
                    page++; // 페이지 증가
                }
            })
            .catch(error => console.error('Error fetching data:', error))
            .finally(() => {
                loading = false; // 로딩 종료
                loadingIndicator.style.display = 'none'; // 로딩 중 엘리먼트 숨김
            });
    }, 500); // 로딩 시간 0.5초 설정
}

function checkScroll() {
    const scrollY = window.scrollY || window.pageYOffset;
    const windowInnerHeight = window.innerHeight;
    const contentHeight = container.offsetHeight;

    if (scrollY + windowInnerHeight >= contentHeight) {
        fetchMoreData();
    }
}

window.addEventListener('scroll', checkScroll);
