document.addEventListener('DOMContentLoaded', () => {
        const dropdownLink = document.querySelector('#aboutDropdown');

        dropdownLink.addEventListener('click', (event) => {
            // 드롭다운 메뉴 열려 있으면 클릭 시 링크 이동
            if (dropdownLink.parentElement.classList.contains('show')) {
                window.location.href = dropdownLink.href;
            }
        });
    });