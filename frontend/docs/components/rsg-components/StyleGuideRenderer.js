import React from 'react';
import PropTypes from 'prop-types';
import Version from 'rsg-components/Version';
import Markdown from 'rsg-components/Markdown';
import Logo from 'rsg-components/Logo';
import Styled from 'rsg-components/Styled';

const xsmall = '@media (max-width: 600px)';

const styles = ({ font, maxWidth, mq, space, color }) => ({
    root: {
		minHeight: '100vh',
		backgroundColor: color.baseBackground,
	},
    header: {
        color: '#41b883',
        backgroundColor: 'white',
        padding: [[space[2], 0]],
        marginBottom: space[4],
        boxShadow: '0 2px 6px 0 rgb(0 0 0 / 16%)',
    },
    headerLink: {
        '&, &:link, &:visited': {
            marginLeft: '0.5em',
            marginRight: '0.5em',
            fontFamily: font,
            color: '#41b883',
        },
        '&:hover, &:active': {
            color: '#35495e',
            cursor: 'pointer',
        },
    },
    nav: {
        marginLeft: 'auto',
        marginRight: '-0.5em',
        [xsmall]: {
            margin: [[10, 0, 0]],
        },
    },

    logoWrapper: {
        padding: space[2],
        borderBottom: [[1, color.border, 'solid']],
    },

    bar: {
        display: 'flex',
        alignItems: 'center',
        [xsmall]: {
            flexDirection: 'column',
            alignItems: 'center',
        },
    },

    sidebar: {
        flex: '0 0 auto',
        width: '20%',
    },

    sections: {
        flex: '0 0 auto',
        width: '80%',
        paddingLeft: space[4],
    },

    contentWrapper: {
		maxWidth,
		padding: [[0, space[2]]],
		margin: [[0, 'auto']],
		[mq.small]: {
			padding: space[2],
		},
		display: 'block',
	},

    content:{
        display: 'flex',
        flexWrap: 'wrap',
    },

    components: {
        overflow: 'auto', // To prevent the pane from growing out of the screen
    },
});

export function StyleGuideRenderer({
    classes,
    title,
    children,
    version,
    homepageUrl,
    toc,
    hasSidebar,
}) {
    return (
        <div className={classes.root}>
            <header className={classes.header}>
                <div className={classes.contentWrapper}>
                    <div className={classes.bar}>
                        <div className={classes.logoWrapper}>
                            <Logo>{title}</Logo>
                        </div>
                        <nav className={classes.nav}>
                            <a
                                className={classes.headerLink}
                                href="https://vue-styleguidist.github.io"
                            >
                                Docs
                            </a>
                            <a
                                className={classes.headerLink}
                                href="https://github.com/vue-styleguidist/vue-styleguidist"
                            >
                                GitHub
                            </a>
                        </nav>
                    </div>
                </div>
            </header>
            <main className={classes.contentWrapper}>
                <div className={classes.content}>
                    {hasSidebar && (
                        <div className={classes.sidebar} data-testid="sidebar">
                            {toc}
                            {/* {version && <Version>{version}</Version>} */}
                        </div>
                    )}
                    <div className={classes.sections} data-testid="sections">
                        {children}
                    </div>
                </div>

                <footer className={classes.footer}>
                    <Markdown text={`Created with [React Styleguidist](${homepageUrl})`} />
                </footer>
            </main>
        </div>
    );
}

StyleGuideRenderer.propTypes = {
    classes: PropTypes.object.isRequired,
    title: PropTypes.string.isRequired,
    version: PropTypes.string,
    homepageUrl: PropTypes.string.isRequired,
    children: PropTypes.node.isRequired,
    toc: PropTypes.node.isRequired,
    hasSidebar: PropTypes.bool,
};

export default Styled(styles)(StyleGuideRenderer);
