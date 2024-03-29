$(".parallax").scrollTop(getCookie("indexScrollPosition"));

$( document ).ready(function() {
    var popupActivator = document.getElementById("popoverData").dataset.name;
    var myPopover = document.getElementById("myPopover");
    var cards =  document.getElementsByClassName('group list-group-image');
    var loginButton = document.getElementById('login');
    var logoutButton = document.getElementById('logout');



    loginButton.addEventListener('click', function () {
        document.cookie="indexScrollPosition=" + $(".parallax").scrollTop();
    });

    logoutButton.addEventListener('click', function () {
        document.cookie="indexScrollPosition=" + $(".parallax").scrollTop();
    });

    myPopover.addEventListener('mouseenter', function () {
        $(this).popover({
            placement: 'left'
        });
        $(this).attr('data-content', $('#popper-content').html());
        $(this).attr('data-original-title', 'Items in your cart:');



        console.log(document.getElementById('myPopover'));

        $("#myPopover").popover('show');
        }

    );
    myPopover.addEventListener('mouseleave', function () {
        $("#myPopover").popover('hide');
        console.log('hide')
    });



    if (popupActivator === 'active') {
        $("#myPopover").popover({
            placement: 'left'
        });

        moveCardToCart(cards);

        setTimeout(function () {
            $("#myPopover").popover('show');
            window.setTimeout(function () {
                $(".popover").fadeOut('slow');
            }, 2000);
        }, 1500);

        document.getElementById("popoverData").dataset.name = 'passive';
    }

    var addButtons = document.getElementsByClassName('btn btn-success addButton');
    var catButtons = document.getElementsByClassName('btn btn-default');

    for (var i = 0; i < addButtons.length; i++) {
        addButtons[i].addEventListener('click', function () {
            document.cookie="indexScrollPosition=" + $(".parallax").scrollTop();
            
        })
    }
    for (var i = 0; i < catButtons.length; i++) {
        catButtons[i].addEventListener('click', function () {
            document.cookie= "indexScrollPosition=" + $(".parallax").scrollTop();
        })
    }

    

});


function moveCardToCart(elements) {
    var cart = $('#myPopover');
    var elementToMove = $(getElementToMove(elements));
    var startingWidth = elementToMove.width();
    var startingHeight = elementToMove.height();
    var finalWidth = $('#myPopover').width();
    var finalHeight = $('#myPopover').height();
    var startingPositionTop = elementToMove.offset().top - $(window).scrollTop();
    var startingPositionLeft = elementToMove.offset().left;
    var finalPositionTop = cart.offset().top - 50 - $(window).scrollTop();
    var finalPositionLeft = cart.offset().left + 10;



    if (elementToMove) {
        var imgclone = elementToMove.clone();
        imgclone.addClass('animatedClone');
        console.log(startingPositionTop);
        imgclone
            .offset({
                top: startingPositionTop,
                left: startingPositionLeft
            })
            .css({
                'opacity': '1',
                'position': 'absolute',
                'width': startingWidth,
                'height': startingHeight,
                'z-index': '2'
            })
            .appendTo($('#window'))
            .animate({
                'top': finalPositionTop,
                'left': finalPositionLeft,
                'width': finalWidth,
                'height': finalHeight

            }, 1000, 'easeInOutExpo');

        setTimeout( function () {
            $('#myPopover').addClass("animated");
            window.setTimeout( function () {
                $('#myPopover').removeClass("animated");
            }, 1000);
        }, 1000);



        imgclone.animate({
            'width': '20px',
            'height': '20px',
            'top': finalPositionTop + 40,
            'left': finalPositionLeft + 10,
            'z-index': '0'
        }, function () {
            $(this).detach()
        });
    }
}


function getElementToMove(elements) {
    console.log(document.getElementById('popupContentId').dataset.id);
    var elementId =  document.getElementById('popupContentId').dataset.id;
    for (let i = 0; i < elements.length; i++) {
        let element = elements[i];
        let elementToMoveId = element.getAttribute('src').charAt(element.getAttribute('src').length - 5);
        if (elementId === elementToMoveId) {
            return element;
        }
    }
}


function getCookie(name) {
    let value = "; " + document.cookie;
    let parts = value.split("; " + name + "=");
    if (parts.length === 2) return parts.pop().split(";").shift();
}




