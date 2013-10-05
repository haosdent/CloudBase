var app = new App('hello');

var cb = function(){
    $('#point').text('Click Count:' + this.count);
};

var model = app.create('one', 'hi', cb);
$('#point').click(function(){
    if(model.count !== undefined){
        model.count++;
        model.save();
    }else{
        model.count = 0;
        model.save();
    };
});