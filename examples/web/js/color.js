var app = new App('example');

var model = app.create('1', 'color', function(){
                var html = '';
                for(var k in this){
                    var v = this[k];
                    if(typeof v !== 'function' && k !== 'id' && k !== 'name'){
                        html += '<li><input type="color" value="' + k + '" disabled><span>' + v + '次选择</span></li>';
                    };
                };
                $('#colors').html(html);
            });

$('#colors').on('click', 'li', function(){
    var kind = $('input', this).val();
    model[kind]++;
    model.save();
});

$('#add-color').click(function(){
    model[$('#color-select').val()] = 1;
    model.save();
});