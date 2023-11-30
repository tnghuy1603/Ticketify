const modal = document.querySelector('.login-modal');
const loginBtn = document.querySelector('.login-button');
const closeBtn = document.querySelector('.login-close-btn');

function showLoginForm() {
    modal.classList.add('show');
}

function hideLoginForm() {
    modal.classList.remove('show');
}

console.log(loginBtn);

loginBtn.addEventListener("click", showLoginForm());