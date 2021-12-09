<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-video.ftl">

<#macro video videoId>
    <vs-video
        video-id="${videoId}"
        language="${locale}"
        single-minute-descriptor="${label('video', 'video.minute-text')}"
        plural-minute-descriptor="${label('video', 'video.minutes-text')}"
    />
</#macro>
