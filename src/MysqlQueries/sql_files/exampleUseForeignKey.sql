select user.UserID, user.Firstname, user.Lastname from user left join login_info on login_info.Username = 'SudoKillall';