/*$('#tabs').tabs({
      beforeLoad: function( event, ui ) {
	 ui.jqXHR.fail(function() {
	ui.panel.html(
			"Couldn't load this tab. We'll try to fix this as soon as possible. " );
		});
	},
	collapsible : true
	
    }).find('.ui-tabs-nav').sortable({axis: 'x'});
  
*/
$('#tabs').tabs({
        load: function(event, ui) {
            $(ui.panel).delegate('a', 'click', function(event) {
                $(ui.panel).load(this.href);
                event.preventDefault();
            });
        }
    });

    $("#tabs").bind('tabsselect', function(event, ui) {
        window.location.href=ui.tab;
    });


