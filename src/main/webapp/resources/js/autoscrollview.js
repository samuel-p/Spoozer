function AutoScrollView(view, text) {
    var content = '<span style="margin-right: 50px;">' + text + '</span><span style="margin-right: 50px;" class="fa fa-music" />';
    var outerView = $('<div>').css({
        'width': '100%',
        'white-space': 'nowrap',
        'overflow': 'hidden'
    });
    var innerView = $('<div>').css({
        'float': 'left',
        'white-space': 'nowrap'
    });

    view.html(outerView);
    outerView.append(innerView);
    fill();
    animate();

    function animate() {
        var items = innerView.find('span');
        var item = $(items[0]);
        item.animate({
            'margin-left': (-item.outerWidth(true)) + 'px'
        }, 30 * item.outerWidth(true), 'linear', function () {
            item.appendTo(innerView);
            item.css('margin-left', '0px');
            animate();
        });
    }

    function fill() {
        var count = 0;
        while (innerView.outerWidth(true) < outerView.innerWidth() * 2 && count < 100) {
            innerView.append(content);
            count++;
        }
    }

    $(window).resize(function () {
        setTimeout(function () {
            fill();
        }, 0);
    });
}