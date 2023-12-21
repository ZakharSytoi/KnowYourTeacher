document.addEventListener("DOMContentLoaded", function () {
    const burgerMenu = document.querySelector(".menu-icon-wrapper");
    const navLinks = document.querySelector(".burger-menu-container");

    burgerMenu.addEventListener("click", function () {
        navLinks.classList.toggle("show");
    });
});

// -------------------------STAR-RATING----------------------------------

const ratings = document.querySelectorAll(".rating");
if (ratings.length > 0) {
    initRatings();
}

function initRatings() {
    let ratingActive, ratingValue;

    for (let index = 0; index < ratings.length; index++) {
        const rating = ratings[index];
        initRating(rating);
    }
    function initRating(rating) {
        initRatingVars(rating);

        setRatingActiveWidth();
    }

    function initRatingVars(rating) {
        ratingActive = rating.querySelector(".rating-active");
        ratingValue = rating.querySelector(".rating-value");
    }

    function setRatingActiveWidth(index = ratingValue.innerHTML) {
        const ratingActiveWidth = index / 0.05;
        ratingActive.style.width = `${ratingActiveWidth}%`;
    }
}
