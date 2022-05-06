select user.Firstname, user.Lastname, user.UserID 
from user, login_info
where login_info.Username = 'Sudokillall' && login_info.UserID = user.UserID;
