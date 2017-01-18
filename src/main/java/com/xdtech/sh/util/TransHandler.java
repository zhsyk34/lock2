package com.xdtech.sh.util;

import com.dnake.service.GatewayService;
import com.dnake.service.LockService;
import com.dnk.smart.communication.vo.TcpCtrl;

public class TransHandler {

//	private RecordService recordService;
//
//	private StatusService statusService;

	private LockService lockService;

	private GatewayService gatewayService;

//	private ConfigService configService;
//
//	private GatewayVersionService gatewayVersionService;

//	public void init() {
//		ApplicationContext context = ApplicationContextUtil.getContext();
//		recordService = (RecordService) context.getBean("recordServiceImpl");
//		statusService = (StatusService) context.getBean("statusServiceImpl");
//		lockService = (LockService) context.getBean("lockServiceImpl");
//		gatewayService = (GatewayService) context.getBean("gatewayServiceImpl");
//		configService = (ConfigService) context.getBean("configServiceImpl");
//		gatewayVersionService = (GatewayVersionService) context.getBean("gatewayVersionServiceImpl");
//	}

	// TODO:报警信息
	public void handlerRecord(TcpCtrl ask) {
//		init();
//		String strType = ask.getAction();
//		StringBuffer sb = new StringBuffer();
//		if ("cmtLockRecord".equals(strType) || "cmtUnlock".equals(strType) || "cmtLockStat".equals(strType)) {
//			Lock lock = lockService.findBySn(ask.getDevSN(), ask.getDevNo());
//			if (lock != null) {
//				String uuid = lock.getUuid();
//				if (uuid != null && !"".equals(uuid)) {
//					if ("cmtLockRecord".equals(strType)) {
//						List<LocalRecord> localRecords = new ArrayList<LocalRecord>();
//
//						if (ask.getRecordList() != null && ask.getRecordList().size() > 0) {
//							for (com.dnk.smart.communication.vo.LockRecord lr : ask.getRecordList()) {
//								LocalRecord local = new LocalRecord();
//
//								local.setUuid(uuid);
//								local.setType(FormatUtil.getEnum(ActionType.class, lr.getType()));
//								local.setAction(Action.OPEN);
//								local.setPass(lr.getID());
//								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//								Date time = null;
//
//								try {
//									time = sdf.parse(lr.getTime());
//								} catch (ParseException e) {
//									e.printStackTrace();
//								}
//
//								local.setEventTime(time);
//								sb.append(lr.getTime());
//								sb.append(" ");
//								sb.append(local.getType().getDescription());
//								local.setDescription(sb.toString());
//								sb.delete(0, sb.length());
//								localRecords.add(local);
//							}
//
//							recordService.saveLocalRecords(localRecords);
//						}
//					} else if ("cmtUnlock".equals(strType)) {
//						Record record = new Record();
//
//						record.setUuid(uuid);
//						record.setType(FormatUtil.getEnum(ActionType.class, ask.getType()));
//						record.setAction(Action.OPEN);
//						record.setPass(ask.getID());
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						Date time = null;
//
//						try {
//							time = sdf.parse(ask.getTime());
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//
//						record.setEventTime(time);
//						sb.append(ask.getTime());
//						sb.append(" ");
//						sb.append(record.getType().getDescription());
//						record.setDescription(sb.toString());
//						sb.delete(0, sb.length());
//						recordService.saveRecord(record);
//					} else {
//						Status status = statusService.find(uuid, null);
//						boolean save = false;
//
//						if (status == null) {
//							status = new Status();
//							save = true;
//						}
//
//						status.setUuid(uuid);
//						status.setLocked(ask.getDoor());
//						status.setUpLock(ask.getUplock());
//						status.setBackLock(ask.getBackLock());
//						status.setVoltage(Math.round(ask.getVoltage()));
//						status.setOnline(ask.getOnline());
//						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//						Date time = null;
//
//						try {
//							time = sdf.parse(ask.getTime());
//						} catch (ParseException e) {
//							e.printStackTrace();
//						}
//
//						status.setTime(time);
//
//						if (save) {
//							statusService.save(status);
//						} else {
//							statusService.update(status);
//						}
//					}
//				}
//			}
//		} else if ("getVersion".equals(strType)) {
//			String udid = "";
//
//			Gateway gateway = gatewayService.findBySn(ask.getDevSN());
//			if (gateway != null) {
//				List<Config> list = configService.findList(-1, -1);
//				String[] param = new String[6];
//				udid = gateway.getUdid();
//
//				for (Config configs : list) {
//					String code = configs.getName();
//					if ("serverIP".equals(code)) {
//						param[0] = configs.getValue();
//					} else if ("serverPort".equals(code)) {
//						param[1] = configs.getValue();
//					} else if ("ntpIP0".equals(code)) {
//						param[2] = configs.getValue();
//					} else if ("ntpIP1".equals(code)) {
//						param[3] = configs.getValue();
//					} else if ("ntpPort".equals(code)) {
//						param[4] = configs.getValue();
//					} else if ("weatherURL".equals(code)) {
//						param[5] = configs.getValue();
//					}
//				}
//
//				UdpInfo client = UdpClientsMonitor.clients.get(udid);
//				JsonObject jsonObject = new JsonObject();
//				jsonObject.addProperty("result", "ok");
//				jsonObject.addProperty("flag", ask.getFlag());
//				jsonObject.addProperty("udid", client.getUdid());
//
//				if (gateway.getVersion() != null && !"".equals(gateway.getVersion())) {
//					GatewayVersion version = gatewayVersionService.find(gateway.getVersion());
//					if (version != null) {
//						jsonObject.addProperty("appVersionNo", version.getVersionNo());
//						jsonObject.addProperty("appVersion", version.getVersion());
//						jsonObject.addProperty("appURL", version.getUrl());
//					} else {
//						jsonObject.addProperty("appVersionNo", "v1.0");
//						jsonObject.addProperty("appVersion", "");
//						jsonObject.addProperty("appURL", "");
//					}
//				} else {
//					jsonObject.addProperty("appVersionNo", "v1.0");
//					jsonObject.addProperty("appVersion", "");
//					jsonObject.addProperty("appURL", "");
//				}
//
//				jsonObject.addProperty("serverIP", param[0]);
//				jsonObject.addProperty("serverPort", Integer.valueOf(param[1]));
//				jsonObject.addProperty("ntpIP0", param[2]);
//				jsonObject.addProperty("ntpIP1", param[3]);
//				jsonObject.addProperty("ntpPort", Integer.valueOf(param[4]));
//				jsonObject.addProperty("weatherURL", param[5]);
//				String jsonStr = jsonObject.toString();
//
//				TcpSendHandler.send(client.getSocket(), jsonStr);
//			}
//		}
	}
}
