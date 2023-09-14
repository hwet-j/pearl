const container = document.querySelector('.scroller');
let page = 2; // 시작 페이지 번호 (2부터 시작)
let loading = false;
const loadingIndicator = document.getElementById('loadingIndicator');

function adjustSidebarHeight() {
    var mainContentHeight = document.querySelector('.main-container').offsetHeight;
    document.querySelector('.sidebar').style.height = mainContentHeight + 'px';
}

function fetchMoreData() {
    if (loading) return; // 이미 로딩 중이라면 중복 호출 방지
    loading = true; // 로딩 시작
   /* loadingIndicator.style.display = 'block'; // 로딩 중 엘리먼트 표시*/

    setTimeout(() => {
        fetch(`/main/getMore/${page}`)
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

                        const genreDiv = document.createElement('div');
                        genreDiv.classList.add('genreDiv');

                            const genreBox =document.createElement('div');
                            genreBox.classList.add('genreBox');
                            genreBox.textContent = musicAuction.genre.name;

                            const rightspace =document.createElement('div');
                            rightspace.classList.add('genreDiv-rightspace');

                            const wishButtonContainer = document.createElement('div');
                            if(musicAuction.wish !== -1) {
                                const wishButton = document.createElement('button');
                                wishButton.classList.add('heart-button');
                                if(musicAuction.wish === 1) {
                                    wishButton.classList.add('filled');
                                }
                                wishButton.onclick = function(event) {
                                    event.stopPropagation();
                                    toggleWish(musicAuction.id, event);
                                };

                                const svgElement = document.createElementNS('http://www.w3.org/2000/svg', 'svg');
                                svgElement.setAttribute('width', '25');
                                svgElement.setAttribute('height', '25');
                                svgElement.setAttribute('viewBox', '0 0 24 24');
                                svgElement.setAttribute('fill', musicAuction.wish === 1 ? '#9A97FF' : 'none');
                                svgElement.setAttribute('stroke', '#9A97FF');
                                svgElement.setAttribute('stroke-width', '2');
                                svgElement.setAttribute('stroke-linecap', 'round');
                                svgElement.setAttribute('stroke-linejoin', 'round');

                                const pathElement = document.createElementNS('http://www.w3.org/2000/svg', 'path');
                                pathElement.setAttribute('d', "M12 21.35l-1.45-1.32C5.4 16.25 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 7.75-8.55 11.54L12 21.35z");
                                svgElement.appendChild(pathElement);

                                wishButton.appendChild(svgElement);
                                wishButtonContainer.appendChild(wishButton);
                            }
                            rightspace.appendChild(wishButtonContainer);



                        genreDiv.appendChild(genreBox);
                        genreDiv.appendChild(rightspace);

                        const top = document.createElement('div');
                        top.classList.add('top');
                        top.textContent = musicAuction.title;

                        const bottom = document.createElement('div');
                        bottom.classList.add('bottom');
                        bottom.textContent = musicAuction.authorNickname.nickname;

                        card.appendChild(albumImage);
                        card.appendChild(genreDiv);
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
                /*loadingIndicator.style.display = 'none'; // 로딩 중 엘리먼트 숨김*/
                adjustSidebarHeight();//사이드바 연장
            });

    }, 100); // 로딩 시간 0.1초 설정

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
