<#include "../../include/imports.ftl">
<@hst.setBundle basename="key.locations,key.facilities"/>

<#-- @ftlvariable name="document" type="org.example.beans.Itinerary" -->
<#-- @ftlvariable name="elm" type="org.example.beans.DayD" -->
<#-- @ftlvariable name="stopDirection" type="org.example.beans.StopDirectionsD" -->
<#-- @ftlvariable name="stop" type="org.example.beans.StopD" -->
<#-- @ftlvariable name="facility" type="com.visitscotland.dataobjects.Facility" -->

<style>
    body {
        background-color: #ffd9b3;
    }
</style>

<#if document??>
 <img src="https://instagram.com/p/BhhN3PZHq2Z/media/?size=m" >

     <div class="row">
         <div class="col-sm-6">
             <blockquote class="instagram-media" data-instgrm-captioned data-instgrm-permalink="https://www.instagram.com/p/BhhN3PZHq2Z/?utm_source=ig_embed&amp;utm_campaign=loading" data-instgrm-version="12" style=" background:#FFF; border:0; border-radius:3px; box-shadow:0 0 1px 0 rgba(0,0,0,0.5),0 1px 10px 0 rgba(0,0,0,0.15); margin: 1px; max-width:658px; min-width:326px; padding:0; width:99.375%; width:-webkit-calc(100% - 2px); width:calc(100% - 2px);"><div style="padding:16px;"> <a href="https://www.instagram.com/p/BhhN3PZHq2Z/?utm_source=ig_embed&amp;utm_campaign=loading" style=" background:#FFFFFF; line-height:0; padding:0 0; text-align:center; text-decoration:none; width:100%;" target="_blank"> <div style=" display: flex; flex-direction: row; align-items: center;"> <div style="background-color: #F4F4F4; border-radius: 50%; flex-grow: 0; height: 40px; margin-right: 14px; width: 40px;"></div> <div style="display: flex; flex-direction: column; flex-grow: 1; justify-content: center;"> <div style=" background-color: #F4F4F4; border-radius: 4px; flex-grow: 0; height: 14px; margin-bottom: 6px; width: 100px;"></div> <div style=" background-color: #F4F4F4; border-radius: 4px; flex-grow: 0; height: 14px; width: 60px;"></div></div></div><div style="padding: 19% 0;"></div> <div style="display:block; height:50px; margin:0 auto 12px; width:50px;"><svg width="50px" height="50px" viewBox="0 0 60 60" version="1.1" xmlns="https://www.w3.org/2000/svg" xmlns:xlink="https://www.w3.org/1999/xlink"><g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"><g transform="translate(-511.000000, -20.000000)" fill="#000000"><g><path d="M556.869,30.41 C554.814,30.41 553.148,32.076 553.148,34.131 C553.148,36.186 554.814,37.852 556.869,37.852 C558.924,37.852 560.59,36.186 560.59,34.131 C560.59,32.076 558.924,30.41 556.869,30.41 M541,60.657 C535.114,60.657 530.342,55.887 530.342,50 C530.342,44.114 535.114,39.342 541,39.342 C546.887,39.342 551.658,44.114 551.658,50 C551.658,55.887 546.887,60.657 541,60.657 M541,33.886 C532.1,33.886 524.886,41.1 524.886,50 C524.886,58.899 532.1,66.113 541,66.113 C549.9,66.113 557.115,58.899 557.115,50 C557.115,41.1 549.9,33.886 541,33.886 M565.378,62.101 C565.244,65.022 564.756,66.606 564.346,67.663 C563.803,69.06 563.154,70.057 562.106,71.106 C561.058,72.155 560.06,72.803 558.662,73.347 C557.607,73.757 556.021,74.244 553.102,74.378 C549.944,74.521 548.997,74.552 541,74.552 C533.003,74.552 532.056,74.521 528.898,74.378 C525.979,74.244 524.393,73.757 523.338,73.347 C521.94,72.803 520.942,72.155 519.894,71.106 C518.846,70.057 518.197,69.06 517.654,67.663 C517.244,66.606 516.755,65.022 516.623,62.101 C516.479,58.943 516.448,57.996 516.448,50 C516.448,42.003 516.479,41.056 516.623,37.899 C516.755,34.978 517.244,33.391 517.654,32.338 C518.197,30.938 518.846,29.942 519.894,28.894 C520.942,27.846 521.94,27.196 523.338,26.654 C524.393,26.244 525.979,25.756 528.898,25.623 C532.057,25.479 533.004,25.448 541,25.448 C548.997,25.448 549.943,25.479 553.102,25.623 C556.021,25.756 557.607,26.244 558.662,26.654 C560.06,27.196 561.058,27.846 562.106,28.894 C563.154,29.942 563.803,30.938 564.346,32.338 C564.756,33.391 565.244,34.978 565.378,37.899 C565.522,41.056 565.552,42.003 565.552,50 C565.552,57.996 565.522,58.943 565.378,62.101 M570.82,37.631 C570.674,34.438 570.167,32.258 569.425,30.349 C568.659,28.377 567.633,26.702 565.965,25.035 C564.297,23.368 562.623,22.342 560.652,21.575 C558.743,20.834 556.562,20.326 553.369,20.18 C550.169,20.033 549.148,20 541,20 C532.853,20 531.831,20.033 528.631,20.18 C525.438,20.326 523.257,20.834 521.349,21.575 C519.376,22.342 517.703,23.368 516.035,25.035 C514.368,26.702 513.342,28.377 512.574,30.349 C511.834,32.258 511.326,34.438 511.181,37.631 C511.035,40.831 511,41.851 511,50 C511,58.147 511.035,59.17 511.181,62.369 C511.326,65.562 511.834,67.743 512.574,69.651 C513.342,71.625 514.368,73.296 516.035,74.965 C517.703,76.634 519.376,77.658 521.349,78.425 C523.257,79.167 525.438,79.673 528.631,79.82 C531.831,79.965 532.853,80.001 541,80.001 C549.148,80.001 550.169,79.965 553.369,79.82 C556.562,79.673 558.743,79.167 560.652,78.425 C562.623,77.658 564.297,76.634 565.965,74.965 C567.633,73.296 568.659,71.625 569.425,69.651 C570.167,67.743 570.674,65.562 570.82,62.369 C570.966,59.17 571,58.147 571,50 C571,41.851 570.966,40.831 570.82,37.631"></path></g></g></g></svg></div><div style="padding-top: 8px;"> <div style=" color:#3897f0; font-family:Arial,sans-serif; font-size:14px; font-style:normal; font-weight:550; line-height:18px;"> View this post on Instagram</div></div><div style="padding: 12.5% 0;"></div> <div style="display: flex; flex-direction: row; margin-bottom: 14px; align-items: center;"><div> <div style="background-color: #F4F4F4; border-radius: 50%; height: 12.5px; width: 12.5px; transform: translateX(0px) translateY(7px);"></div> <div style="background-color: #F4F4F4; height: 12.5px; transform: rotate(-45deg) translateX(3px) translateY(1px); width: 12.5px; flex-grow:

         </div>
         <div class="col-sm-6">
             <blockquote class="instagram-media" data-instgrm-permalink="https://www.instagram.com/p/BhhN3PZHq2Z/?utm_source=ig_embed&amp;utm_campaign=loading" data-instgrm-version="12" style=" background:#FFF; border:0; border-radius:3px; box-shadow:0 0 1px 0 rgba(0,0,0,0.5),0 1px 10px 0 rgba(0,0,0,0.15); margin: 1px; max-width:658px; min-width:326px; padding:0; width:99.375%; width:-webkit-calc(100% - 2px); width:calc(100% - 2px);"><div style="padding:16px;"> <a href="https://www.instagram.com/p/BhhN3PZHq2Z/?utm_source=ig_embed&amp;utm_campaign=loading" style=" background:#FFFFFF; line-height:0; padding:0 0; text-align:center; text-decoration:none; width:100%;" target="_blank"> <div style=" display: flex; flex-direction: row; align-items: center;"> <div style="background-color: #F4F4F4; border-radius: 50%; flex-grow: 0; height: 40px; margin-right: 14px; width: 40px;"></div> <div style="display: flex; flex-direction: column; flex-grow: 1; justify-content: center;"> <div style=" background-color: #F4F4F4; border-radius: 4px; flex-grow: 0; height: 14px; margin-bottom: 6px; width: 100px;"></div> <div style=" background-color: #F4F4F4; border-radius: 4px; flex-grow: 0; height: 14px; width: 60px;"></div></div></div><div style="padding: 19% 0;"></div> <div style="display:block; height:50px; margin:0 auto 12px; width:50px;"><svg width="50px" height="50px" viewBox="0 0 60 60" version="1.1" xmlns="https://www.w3.org/2000/svg" xmlns:xlink="https://www.w3.org/1999/xlink"><g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"><g transform="translate(-511.000000, -20.000000)" fill="#000000"><g><path d="M556.869,30.41 C554.814,30.41 553.148,32.076 553.148,34.131 C553.148,36.186 554.814,37.852 556.869,37.852 C558.924,37.852 560.59,36.186 560.59,34.131 C560.59,32.076 558.924,30.41 556.869,30.41 M541,60.657 C535.114,60.657 530.342,55.887 530.342,50 C530.342,44.114 535.114,39.342 541,39.342 C546.887,39.342 551.658,44.114 551.658,50 C551.658,55.887 546.887,60.657 541,60.657 M541,33.886 C532.1,33.886 524.886,41.1 524.886,50 C524.886,58.899 532.1,66.113 541,66.113 C549.9,66.113 557.115,58.899 557.115,50 C557.115,41.1 549.9,33.886 541,33.886 M565.378,62.101 C565.244,65.022 564.756,66.606 564.346,67.663 C563.803,69.06 563.154,70.057 562.106,71.106 C561.058,72.155 560.06,72.803 558.662,73.347 C557.607,73.757 556.021,74.244 553.102,74.378 C549.944,74.521 548.997,74.552 541,74.552 C533.003,74.552 532.056,74.521 528.898,74.378 C525.979,74.244 524.393,73.757 523.338,73.347 C521.94,72.803 520.942,72.155 519.894,71.106 C518.846,70.057 518.197,69.06 517.654,67.663 C517.244,66.606 516.755,65.022 516.623,62.101 C516.479,58.943 516.448,57.996 516.448,50 C516.448,42.003 516.479,41.056 516.623,37.899 C516.755,34.978 517.244,33.391 517.654,32.338 C518.197,30.938 518.846,29.942 519.894,28.894 C520.942,27.846 521.94,27.196 523.338,26.654 C524.393,26.244 525.979,25.756 528.898,25.623 C532.057,25.479 533.004,25.448 541,25.448 C548.997,25.448 549.943,25.479 553.102,25.623 C556.021,25.756 557.607,26.244 558.662,26.654 C560.06,27.196 561.058,27.846 562.106,28.894 C563.154,29.942 563.803,30.938 564.346,32.338 C564.756,33.391 565.244,34.978 565.378,37.899 C565.522,41.056 565.552,42.003 565.552,50 C565.552,57.996 565.522,58.943 565.378,62.101 M570.82,37.631 C570.674,34.438 570.167,32.258 569.425,30.349 C568.659,28.377 567.633,26.702 565.965,25.035 C564.297,23.368 562.623,22.342 560.652,21.575 C558.743,20.834 556.562,20.326 553.369,20.18 C550.169,20.033 549.148,20 541,20 C532.853,20 531.831,20.033 528.631,20.18 C525.438,20.326 523.257,20.834 521.349,21.575 C519.376,22.342 517.703,23.368 516.035,25.035 C514.368,26.702 513.342,28.377 512.574,30.349 C511.834,32.258 511.326,34.438 511.181,37.631 C511.035,40.831 511,41.851 511,50 C511,58.147 511.035,59.17 511.181,62.369 C511.326,65.562 511.834,67.743 512.574,69.651 C513.342,71.625 514.368,73.296 516.035,74.965 C517.703,76.634 519.376,77.658 521.349,78.425 C523.257,79.167 525.438,79.673 528.631,79.82 C531.831,79.965 532.853,80.001 541,80.001 C549.148,80.001 550.169,79.965 553.369,79.82 C556.562,79.673 558.743,79.167 560.652,78.425 C562.623,77.658 564.297,76.634 565.965,74.965 C567.633,73.296 568.659,71.625 569.425,69.651 C570.167,67.743 570.674,65.562 570.82,62.369 C570.966,59.17 571,58.147 571,50 C571,41.851 570.966,40.831 570.82,37.631"></path></g></g></g></svg></div><div style="padding-top: 8px;"> <div style=" color:#3897f0; font-family:Arial,sans-serif; font-size:14px; font-style:normal; font-weight:550; line-height:18px;"> View this post on Instagram</div></div><div style="padding: 12.5% 0;"></div> <div style="display: flex; flex-direction: row; margin-bottom: 14px; align-items: center;"><div> <div style="background-color: #F4F4F4; border-radius: 50%; height: 12.5px; width: 12.5px; transform: translateX(0px) translateY(7px);"></div> <div style="background-color: #F4F4F4; height: 12.5px; transform: rotate(-45deg) translateX(3px) translateY(1px); width: 12.5px; flex-grow: 0; margin-right: 14px;
             <script async src="//www.instagram.com/embed.js"></script>
         </div>
</div>


    <#assign path>${document.path?substring(33,document.path?index_of("/content/content"))}</#assign>
    <#assign stopsCount = 0>
<article class="has-edit-button">
      <@hst.manageContent hippobean=document documentTemplateQuery="new-document" rootPath="site" defaultPath="${path}" />
        <h1 style="text-align: center">${document.heading?html}</h1>
    <@hst.link var="img" hippobean=document.heroImage.image.original/>
<#--       https://www.visitscotland.com/info/accommodation/search-results?prodtypes=${document.productType}&locplace=${document.dmslocation}&locprox=0&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&cat=inn
       DMS location ${document.dmsLocation}-->
    <#if document.breadcrumb??>
          <p><b> Breadcrumb | Breadcrumb | ${document.breadcrumb}</b>  : (${document.class.simpleName})</p>
    </#if>

        <div class="row">
        <div class="col-sm-6">
        <h4> Days: ${document.days?size} </h4>
                <#if document.introduction??>
                    <h4> <@hst.html hippohtml=document.introduction/> </h4>

                </#if>
</div>
<div class="col-sm-6">
        <img src="${img}" width="100%" style=" float: right;">
          </div>
      </div>

    <#list document.getDays() as elm>
     <div class="has-edit-button" style="padding-top: 3%">
         <@hst.manageContent hippobean=elm />
            <div style="background:whitesmoke;border-style: solid; border-width: thin; padding:1%">

            <h2>Day ${elm?index+1}: ${elm.title}<#if elm.transports?size gt 0 > <h4>    Transport:</h4>
                 <ul>
        <#list elm.transports as transport>
                      <li>${transport}</li>
        </#list>
                 </ul>
    </#if></h2>

         <h3> Summary <@hst.html hippohtml=elm.summary/></h3>
            <h4> <@hst.html hippohtml=document.introduction/> </h4>


        </div>

        <#list elm.stops as stopDirection>


            <#list stopDirection.stop as stop>
                <#assign stopsCount++>
                <#assign latitude = "">
                <#assign longitude = "">
                <#assign prod = "">


                 <div class="has-edit-button" style="border-style: solid; border-width: thin; padding:1%" >
                <@hst.manageContent hippobean=stop />
                <#assign prod = productsMap[stop.stopItem.dmsId]>
                <#assign latitude = prod.latitude>
                <#assign longitude = prod.longitude>
                <#if editMode && !prod?has_content >
                    <h2 style="color: red">The product does not exist in the DMS, please add a valid DMS id</h2>
                </#if>

                     <h3 style="padding-top: 1%">${stopsCount}. ${stop.title}</h3>

                     <@fmt.message key="${prod.locationName}" var="city"/>

            <h4>${city?starts_with("?")?then(prod.locationName, city)}</h4>

            <div class="col-sm-12" style="text-align: center">
                <#if stop.stopItem.getImageItem()??>
                    <@hst.link var="image" hippobean=stop.stopItem.getImageItem().image.original/>
                             <img src="${image}" width="50%" >
                <#else>
                          <img src="${prod.image}" width="50%" >
                </#if>
                 </div>


                     <div style="padding-top: 1%"><@hst.html hippohtml=stop.description/></div>

            <#--  <#if prod.duration??>
                 <h4> Time to explore: ${stop.duration}</h4>
              </#if>-->

                <#if prod.name??>
                         <a  target="_blank"  href="https://www.visitscotland.com${prod.url}">
                        FIND OUT MORE</a>
                </#if>


                <#if stop.html.content?has_content>
                         <div style="background: lightpink">
                        <p> DON'T MISS! </p>
                            <p> <@hst.html hippohtml=stop.html/></p>
                         </div>
                </#if>

                     </br>
                     <p class="glyphicon glyphicon-map-marker" style="padding-top: 1%"> Coordinates: ${prod.latitude},${prod.longitude}</p>

            <div style="position:relative">
                    <img
                                 id="image${stopsCount}"
                                 src="https://www.csp.org.uk/sites/default/files/scotlandx.gif"
                                 data-lat-start="60.566850"
                                 data-lat-end="53.622142"
                                 data-lon-start="-7.151292"
                                 data-lon-end="-0.124849"
                                         />
                                         <svg
                                 class="target"
                                 style="position:absolute"
                                 data-lat="${prod.latitude}"
                                 data-lon="${prod.longitude}"
                                 data-image-id="image${stopsCount}"
                                         >
                                         <circle
                                     r="10"
                                     fill="green"
                                     cx="20"
                                     cy="20"
                             />
                         </svg>
                     </div>


                <#if prod.addressLine1??>
                       </br>
                        <div class="glyphicon glyphicon-map-marker" style="padding-top: 1%"> ${prod.addressLine1}</div>
                </#if>
                     </br>

                <#if prod.price?? && prod.price!="null" >
                            <div class="glyphicon glyphicon-info-sign" style="padding-top: 1%"> ${prod.multiplePrices?then("Prices from","Price")}: ${prod.price}</div>
                </#if>



                <#if prod.facilities?has_content >
                        <h4> Facilities: </h4>
                         <ul>
                    <#list prod.facilities?split(",") as facility>
                              <li><@fmt.message key="${facility}" /></li>
                    </#list>


                         </ul>
                </#if>
                </div>
            </#list>
        </#list>
        <#if prod.name??>
                 <a  target="_blank" class="glyphicon glyphicon-cutlery" href="https://www.visitscotland.com/info/accommodation/search-results?prodtypes=cate&lat=${latitude}&lng=${longitude}&locprox=2&areaproxdist=1&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&order=proximityAsc">
                NEARBY PLACES TO EAT</a>
        </br>
        <a  target="_blank" class="glyphicon glyphicon-bed" href="https://www.visitscotland.com/info/accommodation/search-results?prodtypes=acco&lat=${latitude}&lng=${longitude}&locprox=2&areaproxdist=1&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&order=proximityAsc">
                NEARBY PLACES TO STAY</a>
        </#if>
     </div>
    </#list>
  </article>


<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#elseif editMode>
  <div class="has-edit-button">
        <img src="<@hst.link path="/images/essentials/catalog-component-icons/simple-content.png" />"> Click to edit Simple Content
    <@hst.manageContent documentTemplateQuery="new-content-document" parameterName="document" rootPath="content"/>
  </div>
</#if>



<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
    $( document ).ready(function() {
        $(".target").each(position_target)
    })

    function position_target() {
        let $target = $(this)
        let $image = getImage($target)

        $target.css({
            top: calc_y_position($target, $image),
            left: calc_x_position($target, $image)
        })
    }

    function getTarget() {
        return $("#target")
    }

    function getImage($target) {
        return $("#" + $target.attr("data-image-id"))
    }

    function calc_y_position($target, $image) {
        let y_length = $image.height()
        let y_start = Number($image.attr("data-lat-start"))

        let y_end = Number($image.attr("data-lat-end"))
        let y_coord = Number($target.attr("data-lat"))
        let image_y_offset = $image.position().top

        return image_y_offset + calc_axis_position(y_start, y_end, y_coord, y_length)
    }

    function calc_x_position($target, $image) {

        let x_length = $image.width()
        let x_start = Number($image.attr("data-lon-start"))
        let x_end = Number($image.attr("data-lon-end"))
        let x_coord = Number($target.attr("data-lon"))
        let image_x_offset = $image.position().top

        return image_x_offset + calc_axis_position(x_start, x_end, x_coord, x_length)
    }

    function calc_axis_position(scaleStart, scaleEnd, coord, axisLength) {
        return (coord - scaleStart) * axisLength / (scaleEnd - scaleStart)
    }
</script>

<hr/>

