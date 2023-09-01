const container = document.querySelector('.wrap');
let page = 0; // 시작 페이지 번호 (3부터 시작)
let loading = false;

function fetchMoreData() {
    if (loading) return; // 이미 로딩 중이라면 중복 호출 방지
        loading = true; // 로딩 시작
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
                    albumImage.appendChild(image);

                    const top = document.createElement('div');
                    top.textContent = musicAuction.id;

                    const bottom = document.createElement('div');
                    bottom.textContent = musicAuction.content;

                    card.appendChild(albumImage);
                    card.appendChild(top);
                    card.appendChild(bottom);

                    container.appendChild(card);
                });
                page++; // 페이지 증가
            }
        })
        .catch(error => console.error('Error fetching data:', error));
}

function checkScroll() {
    const scrollY = window.scrollY || window.pageYOffset;
    const windowInnerHeight = window.innerHeight;
    const contentHeight = container.offsetHeight;

    if (scrollY + windowInnerHeight >= contentHeight) {
         setTimeout(fetchMoreData, 300); // 0.3초 후에 데이터 불러오기 시작
    }
}

window.addEventListener('scroll', checkScroll);