function PlayerSlider(slider) {
    var changeable = false;
    var changeCallback;

    this.change = function (callback) {
        changeCallback = callback;
    };

    slider.on('change.fndtn.slider', function () {
        if (changeable) {
            changeCallback.call(this);
        }
    });

    this.get = function () {
        return slider.attr('data-slider');
    };

    this.set = function (value) {
        changeable = false;
        slider.foundation('slider', 'set_value', value);
        setTimeout(function () {
            changeable = true;
        }, 20);
    };
}