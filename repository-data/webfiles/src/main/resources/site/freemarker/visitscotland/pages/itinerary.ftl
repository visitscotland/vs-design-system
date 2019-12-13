<#include "../../include/imports.ftl">
<#include "itinerary-stop.ftl">
<@hst.setBundle basename="keyFacilities"/>

<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Itinerary" -->
<#-- @ftlvariable name="day" type="com.visitscotland.brmx.beans.Day" -->


<style>
    body {
        background-color: #ffd9b3 !important;
    }
</style>

<#if document??>


    <#assign path>${document.path?substring(33,document.path?index_of("/content/content"))}</#assign>
    <#assign stopsCount = 0>
<article class="has-edit-button">
      <@hst.manageContent hippobean=document documentTemplateQuery="new-document" rootPath="site" defaultPath="${path}" />
        <h1 style="text-align: center">${document.heading?html}</h1>
    <@hst.link var="img" hippobean=document.heroImage.original/>
<#--       https://www.visitscotland.com/info/accommodation/search-results?prodtypes=${document.productType}&locplace=${document.dmslocation}&locprox=0&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&cat=inn
       DMS location ${document.dmsLocation}-->
    <#if document.breadcrumb??>
          <p><b> Breadcrumb | Breadcrumb | ${document.breadcrumb}</b>  : (${document.class.simpleName})</p>
    </#if>

        <div class="row">
            <div class="col-sm-6">
            <h4> Days: ${document.days?size} </h4>
                 <#if document.transports?size gt 0 >
                    <h4>Transport: ${document.transports[0]} </h4>
                 </#if>
                 <#if document.distance?? >Distance in miles: ${document.distance}</#if>
                 <#if document.them?? >Main theme: ${document.theme}</#if>
            </div>

            <div class="col-sm-6">
                <img src="${img}" width="100%" style=" float: right;">
            </div>
        </div>

     <#if document.introduction??>
           <h4> <@hst.html hippohtml=document.introduction/> </h4>
     </#if>

        <h3>Start/Finish : ${firstStopLocation}/${lastStopLocation}</h3>

     <#if document.theme??>
         <h4 style="background: aliceblue"> Theme Category: ${document.theme} </h4>
     </#if>

     <#if document.highlights??>
          <h4 style="background: aliceblue"> HIGHLIGHTS:  ${document.highlights} </h4>
     </#if>


        <#list document.getDays() as day>
         <div class="has-edit-button" style="padding-top: 3%">
             <@hst.manageContent hippobean=day />
                <div style="background:whitesmoke;border-style: solid; border-width: thin; padding:1%">

                <h2>Day ${day?index+1}: ${day.title}<#if day.transports?size gt 0 > <h4>    Transport:</h4>
                     <ul>
                        <#list day.transports as transport>
                            <li>${transport}</li>
                        </#list>
                    </ul>
                    </#if>
                </h2>

            <h4><@hst.html hippohtml=day.introduction/></h4>
        </div>


             <ul class="list-unstyled">
                <#list day.stops as stop>
                    <#assign stopsCount++>
                    <@itineraryStop stop=stop stopNumber=stopsCount/>
                </#list>
             </ul>
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

