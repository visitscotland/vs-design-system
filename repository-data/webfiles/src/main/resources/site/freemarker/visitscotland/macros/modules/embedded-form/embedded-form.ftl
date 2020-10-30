<#include "../../../../include/imports.ftl">

<#-- Reference: https://developers.marketo.com/javascript-api/forms/ -->

<#macro embeddedForm marketoId>
    <section class="vs-embedded-form">
        <vs-container>
            <vs-row>
                <vs-col cols="12">
                    <p>Newsletter</p>
                    <form class="vs-form" id="mktoForm_${marketoId}"></form>

                    <@hst.headContribution category="htmlBodyEndScripts">
                        <script src="//e.visitscotland.com/js/forms2/js/forms2.min.js"></script>
                    </@hst.headContribution>

                    <@hst.headContribution category="htmlBodyEndScripts">
                        <script type="text/javascript">
                            <#-- TODO ${label("configuration", "marketo.munchkinId")} -->
                            <#-- TODO Do we need to proxy app-sjqe.marketo.com? -->

                            MktoForms2.loadForm("//e.visitscotland.com", "638-HHZ-510", ${marketoId});    

                            /*
                            * @author Sanford Whiteman
                            * @version v1.104
                            * @license MIT License: This license must appear with all reproductions of this software.
                            *
                            * Create a completely barebones, user-styles-only Marketo form
                            * by removing inline STYLE attributes and disabling STYLE and LINK elements
                            */                       

                            function destyleMktoForm(mktoForm, moreStyles) {
                                var formEl = mktoForm.getFormElem()[0],
                                            arrayify = getSelection.call.bind([].slice);

                                var styledEls = arrayify(formEl.querySelectorAll("[style]")).concat(formEl);    
                                styledEls.forEach(function(el) {
                                    el.removeAttribute("style");
                                });
                                var styleSheets = arrayify(document.styleSheets);   
                                styleSheets.forEach(function(ss) {
                                    if ( [mktoForms2BaseStyle,mktoForms2ThemeStyle].indexOf(ss.ownerNode) != -1 || formEl.contains(ss.ownerNode) ) {
                                        ss.disabled = true;
                                    }
                                });
                                    
                                if(!moreStyles) {
                                    formEl.setAttribute("data-styles-ready", "true");
                                    console.log("Styles ready at: " + performance.now())
                                }
                            };

                            MktoForms2.whenRendered(function(form) {
                                console.log('here')
                                destyleMktoForm(form);
                            });
                            
                        </script>
                    </@hst.headContribution>
                </vs-col>
            </vs-row>
        </vs-container>
    </section>
</#macro>
