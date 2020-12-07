

<#include "../../include/imports.ftl">
<#include "../macros/global/cms-errors.ftl">
<#include "../../frontend/components/vs-hero.ftl">


<#macro heroModule content heroImage heroCoordinates hero>
<vs-page-intro>
<vs-hero
        slot="hero"
        alt-text="${heroImage.altText!''}"
        credit="${heroImage.credit!''}"
        caption="${heroImage.description!''}"
        image-src="${hero}"
        latitude="${(heroCoordinates.latitude)!''}"
        longitude="${(heroCoordinates.longitude)!''}"
>
    <vs-img
            src="${hero}"
            alt="${heroImage.altText!''}"
    > </vs-img>
</vs-hero>
<vs-container slot="upper" class="py-lg-4">
			<vs-row class="justify-content-md-between">
				<vs-col cols="12" lg="8" offset-lg="1">
					<@hst.include ref="breadcrumb"/>
				</vs-col>
			</vs-row>

			<vs-row>
				<vs-col cols="10" lg="8" offset-lg="1">
					<vs-heading level="1">${document.title}</vs-heading>
				</vs-col>
				<vs-col cols="2">
					<div class="d-flex justify-content-center justify-content-sm-end">
						<vs-social-share></vs-social-share>
					</div>
				</vs-col>
			</vs-row>
			<vs-row>
				<vs-col cols="12" md="10" lg="10" xl="10" offset-lg="1">
					<vs-rich-text-wrapper variant="lead">
						<@hst.html hippohtml=document.introduction/>
					</vs-rich-text-wrapper>
				</vs-col>
			</vs-row>
		</vs-container>
</vs-page-intro>
</#macro>