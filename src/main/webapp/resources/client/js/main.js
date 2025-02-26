(function ($) {
    "use strict";

    // Hàm cập nhật tổng đơn hàng
    function updateCartTotal() {
        let total = 0;
        $('.quantity input').each(function () {
            const quantity = parseFloat($(this).val());
            const price = parseFloat($(this).attr("data-cart-detail-price"));
            total += quantity * price;
        });
        $("p[data-cart-total-price]").each(function () {
            $(this).text(formatCurrency(total.toFixed(2)) + " đ");
            $(this).attr("data-cart-total-price", total);
        });
    }

    // Spinner
    var spinner = function () {
        setTimeout(function () {
            if ($('#spinner').length > 0) {
                $('#spinner').removeClass('show');
            }
        }, 1);
    };
    spinner();

    // Fixed Navbar
    $(window).scroll(function () {
        if ($(window).width() < 992) {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow');
            } else {
                $('.fixed-top').removeClass('shadow');
            }
        } else {
            if ($(this).scrollTop() > 55) {
                $('.fixed-top').addClass('shadow').css('top', 0);
            } else {
                $('.fixed-top').removeClass('shadow').css('top', 0);
            }
        }
    });

    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({ scrollTop: 0 }, 1500, 'easeInOutExpo');
        return false;
    });

    // Testimonial carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 2000,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: { items: 1 },
            576: { items: 1 },
            768: { items: 1 },
            992: { items: 2 },
            1200: { items: 2 }
        }
    });

    // Vegetable carousel
    $(".vegetable-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        center: false,
        dots: true,
        loop: true,
        margin: 25,
        nav: true,
        navText: [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsiveClass: true,
        responsive: {
            0: { items: 1 },
            576: { items: 1 },
            768: { items: 2 },
            992: { items: 3 },
            1200: { items: 4 }
        }
    });

    // Modal Video
    $(document).ready(function () {
        var $videoSrc;
        $('.btn-play').click(function () {
            $videoSrc = $(this).data("src");
        });
        $('#videoModal').on('shown.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc + "?autoplay=1&amp;modestbranding=1&amp;showinfo=0");
        });
        $('#videoModal').on('hide.bs.modal', function (e) {
            $("#video").attr('src', $videoSrc);
        });
    });

    // Product Quantity
    $('.quantity button').on('click', function () {
        var button = $(this);
        var $input = button.parent().parent().find('input');
        var oldValue = parseFloat($input.val());
        var newVal;

        if (button.hasClass('btn-plus')) {
            newVal = oldValue + 1;
        } else {
            newVal = (oldValue > 1) ? oldValue - 1 : 1;
        }
        $input.val(newVal);

        // Cập nhật thành tiền cho sản phẩm đó
        const price = parseFloat($input.attr("data-cart-detail-price"));
        const id = $input.attr("data-cart-detail-id");
        const priceElement = $(`p[data-cart-detail-id='${id}']`);
        priceElement.text(formatCurrency((price * newVal).toFixed(2)) + " đ");

        // Cập nhật lại tổng đơn hàng
        updateCartTotal();
    });

    // Hàm định dạng tiền tệ
    function formatCurrency(value) {
        const formatter = new Intl.NumberFormat('vi-VN', {
            style: 'decimal',
            minimumFractionDigits: 0,
        });
        let formatted = formatter.format(value);
        formatted = formatted.replace(/\./g, ',');
        return formatted;
    }

    // Gọi cập nhật tổng đơn hàng khi trang vừa load
    $(document).ready(function () {
        updateCartTotal();
    });

})(jQuery);
