(function(){
    var server = 'ws://localhost:8080';

    var Socket = function(server){
        var that = this;
        this.entity = new WebSocket(server);
        this.entity.onmessage = function(e){
            that.receive(e);
        };
        this.listeners = {};
        this.queue = [];
    };

    Socket.prototype =  {
        send: function(req, isForce){
            req.rid = Date.now();
            if(this.entity.readyState === this.entity.OPEN){
                if(this.queue.length !== 0){
                    var oldReq;
                    for(; (oldReq = this.queue.shift()) !== undefined;){
                        this.entity.send(JSON.stringify(oldReq));
                    };
                };
                this.entity.send(JSON.stringify(req));
            }else if(this.entity.readyState !== this.entity.OPEN && isForce){
                this.queue.push(req);
            };
        }
      , receive: function(e){
            var resp = JSON.parse(e.data);
            var cmd = resp.cmd;
            var gid = resp.gid;
            if(cmd === 'error' || cmd === 'success')
              this[cmd](resp);
            this.listeners[gid][cmd](resp);
        }
      , register: function(gid, obj){
            this.listeners[gid] = obj;
        }
      , error: function(resp){
            console.error('Rid: ' + resp.rid + ', Obj: ' + resp.gid + ', Act: ' + resp.act + ', Result: error!');
        }
      , success: function(resp){
            console.log('Rid: ' + resp.rid + ', Obj: ' + resp.gid + ', Act: ' + resp.act + ', Result: success!');
        }
    };

    var socket = new Socket(server);

    var App = function(name){
        this.name = name;
        socket.register(this.name, this);

        var req = {
            act: 'create'
          , app: this.name
          , gid: this.name
        };
        socket.send(req, true);
    };

    App.prototype = {
        create: function(id, name, cb){
            var model = new Model(this, id, name, cb);
            return model;
        }
      , update: function(resp){
            console.log('Create ' + this.name + ' successfully!');
        }
      , error: function(resp){
        }
      , success: function(resp){
        }
    };

    var Model = function(app, id, name, cb){
        this.name = name;
        this.id = id;
        this.cb = cb;

        var gid = app.name + this.name + this.id;
        var hidden = {app: app, gid: gid};
        socket.register(gid, this);
        this.getter = function(k){
            return hidden[k];
        };
        this.setter = function(k, v){
            hidden[k] = v;
        };

        //this.save();
        this.on();
    };

    Model.prototype = {
        save: function(){
            var version = Date.now();
            var req = {
                act: 'put'
              , app: this.getter('app').name
              , id: this.id
              , gid: this.getter('gid')
              , version: version
              , data: JSON.stringify(this)
            };
            var that = this;

            socket.send(req, true);
        }
      , on: function(cb){
            //this.getter('app').listen(this, cb);
            this.up();
            var that = this;
            var fn = function(){
                var version = that.getter('version');
                (version === undefined) && (version = 0);
                var req = {
                    act: 'get'
                  , app: that.getter('app').name
                  , id: that.id
                  , gid: that.getter('gid')
                  , version: version
                };

                socket.send(req, false);
            };
            var intervalId = setInterval(fn, 100);
            this.setter('intervalId', intervalId);
        }
      , up: function(){
            clearInterval(this.getter('intervalId'));
        }
      , update: function(resp){
            if (!resp.version) return;
            this.setter('version', resp.version);
            var newObj = JSON.parse(resp.data);
            for(var k in newObj){
                this[k] = newObj[k];
            };
            this.cb();
        }
      , error: function(resp){
        }
      , success: function(resp){
        }
    }

    this.App = App;
    this.Model = Model;
}).call(window);