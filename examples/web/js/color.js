client = 'haosdent'
var app = new App('hello');
var cb = function(){
    $('body').text('Model A:' + this.a);
};
var model = app.create('one', 'hi', cb);