(function(){
    var urlPrefix = 'http://localhost:8080';
    var App = function(name){
        this.name = name;
        var url = urlPrefix + '/' + name;
        $.post(url)
        .success(function(){
            console.log('Create app successfully!');
        }).error(function(){
            console.log('Error when create app!');
        });
    };

    App.prototype = {
        create: function(id, name, cb){
            var model = new Model(this, id, name, cb);
            return model;
        }
    };

    var Model = function(app, id, name, cb){
        this.name = name;
        this.id = id;
        var hidden = {app: app};
        this.getter = function(k){
            return hidden[k];
        };
        this.setter = function(k, v){
            hidden[k] = v;
        };
        //this.save();
        this.on(cb);
    };

    Model.prototype = {
        save: function(){
            var version = Date.now();
            var url = urlPrefix + '/' + this.getter('app').name + '/' + this.id + '/' + version;
            var json = JSON.stringify(this);
            var that = this;

            $.ajax({
                url: url
              , type: 'POST'
              , data: 'data=' + json
            }).success(function(){
                console.log('Create model successfully!');
            }).error(function(){
                console.log('Error when create model!');
            });
        }
      , on: function(cb){
            //this.getter('app').listen(this, cb);
            this.up();
            var that = this;
            var ajax = function(){
                var version = that.getter('version');
                (version === undefined) && (version = 0);
                var url = urlPrefix + '/' + that.getter('app').name + '/' + that.id + '/' + version;
                $.get(url)
                .success(function(resp){
                    if (!resp.version) return;
                    that.setter('version', resp.version);
                    var newObj = JSON.parse(resp.data);
                    for(var k in newObj){
                        that[k] = newObj[k];
                    };
                    cb.call(that);
                }).error(function(){
                    console.log('Error when update!');
                }).always(function(){
                    that.setter('timeoutId', setTimeout(ajax, 1000));
                });
            };
            ajax();
        }
      , up: function(){
            clearTimeout(this.getter('timeoutId'));
        }
    }

    this.App = App;
    this.Model = Model;
}).call(window);