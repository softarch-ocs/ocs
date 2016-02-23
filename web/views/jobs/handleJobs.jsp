<%@ taglib prefix="pages" tagdir="/WEB-INF/tags/pages" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">
    <pages:bootstrap/>
    <title>Jobs</title>

</head>

<body>

<pages:header/>

<div class="container">

    <div class="starter-template">
        <form class="form-horizontal">
            <fieldset>

                <!-- Form Name -->
                <legend>New Job</legend>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="nameInput">Job Name</label>
                    <div class="col-md-4">
                        <input id="nameInput" name="nameInput" type="text" placeholder="name" class="form-control input-md" required="">

                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="salaryInput">Salary</label>
                    <div class="col-md-2">
                        <input id="salaryInput" name="salaryInput"  pattern= "[0-9]"  type="text" placeholder="salary" class="form-control input-md" required="">

                    </div>
                </div>

                <!-- Text input-->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="descriptionInput">Description</label>
                    <div class="col-md-5">
                        <textarea id="descriptionInput" name="descriptionInput" type="text" placeholder="job description..." class="form-control input-md" rows="3"></textarea>

                    </div>
                </div>

                <!-- Accept Button -->
                <div class="form-group">
                    <label class="col-md-4 control-label" for="acceptButton"></label>
                    <div class="col-md-4">
                        <button id="acceptButton" name="acceptButton" class="btn btn-success">Save</button>
                    </div>
                </div>

            </fieldset>
        </form>
    </div>
</div><!-- /.container -->

<pages:footer/>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="../../dist/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
