function toggleWish(auctionId, event) {
    event.preventDefault();
    event.stopPropagation();

    const buttonElement = event.target.closest('button');
    const svgElement = buttonElement.querySelector('svg');

    fetch('/detail/list-click-wishbutton?auctionId=' + auctionId, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => response.json())
    .then(data => {
        if(data.message === 'Add') {
            buttonElement.classList.add('filled');
            svgElement.setAttribute('fill', '#9A97FF');
            svgElement.setAttribute('stroke', '#9A97FF');
        } else if(data.message === 'Delete') {
            buttonElement.classList.remove('filled');
            svgElement.setAttribute('fill', 'none');
            svgElement.setAttribute('stroke', '#9A97FF');
        } else {
            console.error('Error:', data.message);
        }
    })
    .catch(error => console.error('Error:', error));
}
