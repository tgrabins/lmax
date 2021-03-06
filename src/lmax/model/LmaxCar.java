package lmax.model;

import com.sun.xml.internal.txw2.IllegalSignatureException;

public class LmaxCar implements Car {

    private final long startTime = System.currentTimeMillis();
    private final long serialNumber;
    private PaddedBoolean chassis = new PaddedBoolean();
    private PaddedBoolean rearAxle = new PaddedBoolean();
    private PaddedBoolean frontLeftSuspension = new PaddedBoolean();
    private PaddedBoolean frontRightSuspension = new PaddedBoolean();
    private PaddedBoolean frontLeftWheel = new PaddedBoolean();
    private PaddedBoolean frontRightWheel = new PaddedBoolean();
    private PaddedBoolean rearLeftWheel = new PaddedBoolean();
    private PaddedBoolean rearRightWheel = new PaddedBoolean();
    private PaddedBoolean body = new PaddedBoolean();

    public LmaxCar(long serialNumber) {
	this.serialNumber = serialNumber;
    }

    @Override
    public boolean isReady() {
	return chassis.isValue()
		&& rearAxle.isValue()
		&& frontLeftSuspension.isValue()
		&& frontRightSuspension.isValue()
		&& frontLeftWheel.isValue()
		&& frontRightWheel.isValue()
		&& rearLeftWheel.isValue()
		&& rearRightWheel.isValue()
		&& body.isValue();
    }

    public void installChassis() {
	chassis.setTrue();
    }

    @Override
    public void installRearAxle() {
	if (chassis.isValue()) {
	    rearAxle.setTrue();
	} else {
	    throw new IllegalSignatureException("chassis not ready");
	}
    }

    @Override
    public void installFrontLeftSuspension() {
	if (chassis.isValue()) {
	    frontLeftSuspension.setTrue();
	} else {
	    throw new IllegalSignatureException("chassis not ready");
	}
    }

    @Override
    public void installFrontRightSuspension() {
	if (chassis.isValue()) {
	    frontRightSuspension.setTrue();
	} else {
	    throw new IllegalSignatureException("chassis not ready");
	}
    }

    @Override
    public void installWheel(boolean front, boolean left) {
	if (front) {
	    if (left) {
		if (frontLeftSuspension.isValue()) {
		    frontLeftWheel.setTrue();
		} else {
		    throw new IllegalSignatureException("frontLeftSuspension not ready");
		}
	    } else {
		if (frontRightSuspension.isValue()) {
		    frontRightWheel.setTrue();
		} else {
		    throw new IllegalSignatureException("rightLeftSuspension not ready");
		}
	    }
	} else {
	    if (rearAxle.isValue()) {
		if (left) {
		    rearLeftWheel.setTrue();
		} else {
		    rearRightWheel.setTrue();
		}
	    } else {
		throw new IllegalSignatureException("rearAxle not ready");
	    }
	}
    }

    @Override
    public void installBody() {
	if (frontLeftWheel.isValue()
		&& frontRightWheel.isValue()
		&& rearLeftWheel.isValue()
		&& rearRightWheel.isValue()) {
	    body.setTrue();
	} else {
	    throw new IllegalSignatureException("wheels not ready");
	}
    }

    @Override
    public String getFinishMsg(long systemStartTime) {
	return "Car " + serialNumber + " isReady: " + isReady() + " in " + (System.currentTimeMillis() - startTime)
		+ "ms. Time from system start:" + (System.currentTimeMillis() - systemStartTime) + "ms";
    }

}
