<html>
<style>
    #article_show {
        background-color: #FFFFFF;
        border-radius: 3px 3px 3px 3px;
        margin: 40px auto 0;
        min-height: 300px;
        position: relative;
        width: 700px;
        z-index: 0;
    }

    #article_show h1 {
        color: #000000;
        font-weight: normal;
        letter-spacing: 4px;
        margin: 0 0 30px;
        position: relative;
        text-align: center;
        text-transform: uppercase;
    }

    .article_author {
        color: #999999 !important;
        margin: 40px 0 50px !important;
        text-align: center !important;
    }

    #article_show p {
        color: #000;
        font-size: 16px;
        font-weight: normal;
        line-height: 30px;
        margin-bottom: 30px;
        text-align: justify;
    }
</style>
<body>
<div id="article_show">
    <h1 style="text-align: center;color: #000000;letter-spacing: 4px;margin: 0 0 30px;">${title}</h1>
    <p style="color: #999999; margin: 40px 0 50px; text-align: center;"><span>${author}</span></p>
    <div style="color: #000;font-size: 16px;font-weight: normal;line-height: 30px;margin-bottom: 30px;text-align: justify;">
        <#list items as item>
            <p>${item}</p>
        </#list>
    </div>
</div>
</body>
</html>
