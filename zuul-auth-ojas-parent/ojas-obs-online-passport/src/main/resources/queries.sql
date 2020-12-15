// ...........................SQL QUERIES for Passport.................//
INSERTPASSPORTSTMT = insert into ojas_obs.obs_passport (centerName) values (?)
GETTOTALSTMT = select * from ojas_obs.obs_passport
UPDATESTMT = update ojas_obs.obs_passport set obs_passport.centerName = ?  where obs_passport.id = ?
COUNTSTMT = select count(*) from ojas_obs.obs_passport
